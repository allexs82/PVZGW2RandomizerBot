package ru.allexs82.listeners

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button
import org.slf4j.LoggerFactory
import ru.allexs82.data.GameMap
import ru.allexs82.data.enums.Modes
import ru.allexs82.data.enums.Sides
import ru.allexs82.utils.DiscordUtils
import ru.allexs82.utils.EncodingUtils
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors
import kotlin.random.Random

private val LOGGER = LoggerFactory.getLogger(RGameEventListener::class.java)
private const val REROLL = "rgame_reroll"
private const val REROLL_EXCLUDE = "rgame_reroll_exclude"
private const val STRING_SELECT_MENU_ID = "rgame_select_modes"
private const val SLASH_COMMAND_NAME = "rgame"

class RGameEventListener : ListenerAdapter() {
    private val sessionsHashMap = ConcurrentHashMap<String, Session>()

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != SLASH_COMMAND_NAME) return
        event.deferReply().queue()

        sessionsHashMap.remove(event.user.id)
        val players = getPlayersFromEvent(event)

        val session = Session(event.user, players)
        sessionsHashMap[event.user.id] = session

        val stringSelectMenu = DiscordUtils.createModesSelectMenu(STRING_SELECT_MENU_ID)

        event.hook.deleteOriginal().submit().thenCompose {
            event.hook
                .sendMessage("Select mode(s)")
                .setActionRow(stringSelectMenu)
                .setEphemeral(true)
                .submit()
        }
    }

    private fun getPlayersFromEvent(event: SlashCommandInteractionEvent): List<User> =
        (2..4).mapNotNull { i -> event.getOption("p$i")?.asUser }

    override fun onStringSelectInteraction(event: StringSelectInteractionEvent) {
        if (event.componentId != STRING_SELECT_MENU_ID) return
        event.deferReply().queue()

        val userId = event.user.id
        val session = sessionsHashMap[userId]
        if (session == null) {
            event.hook.editOriginal("Oops... Couldn't find your session :confused:. Make sure it was you who sent /rgame.")
                .submit()
                .whenComplete { _: Message?, _: Throwable? ->
                    LOGGER.warn("Can't find session for user {}", DiscordUtils.getUserNameAndId(event))
                }
            return
        }
        session.addSelectedModes(event.values
            .stream()
            .map { name: String? -> Modes.valueOf(name!!) }
            .collect(
                Collectors.toCollection { ArrayList() }
            )
        )
        sendResponse(event)
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        val componentId = event.componentId
        if (componentId != REROLL && componentId != REROLL_EXCLUDE) return
        event.deferReply().queue()
        if (componentId == REROLL_EXCLUDE) {
            sessionsHashMap[event.user.id]!!.excludeAndInvalidateLastMap()
        }
        sendResponse(event)
    }

    private fun sendResponse(event: GenericComponentInteractionCreateEvent) {
        val session = sessionsHashMap[event.user.id]
        if (session == null) {
            event.hook.deleteOriginal().submit().thenCompose {
                event.hook.editOriginal("Session not found. Please type /rgame again.").submit()
            }
            LOGGER.error("No session found for user {}", DiscordUtils.getUserNameAndId(event))
            return
        }
        handleSessionResponse(event, session)
    }

    private fun handleSessionResponse(event: GenericComponentInteractionCreateEvent, session: Session) {
        val excludedMaps = session.getExcludedMaps()
        val selectedModes = session.getSelectedModes().toMutableList()

        val randomMap = GameMap.getRandomMapForModes(selectedModes, excludedMaps)
        if (randomMap == null) {
            event.hook.deleteOriginal().submit().thenCompose {
                event.hook.sendMessage("Looks like you are excluded all maps. Please type /rgame again.")
                    .setEphemeral(true).submit()
            }

            LOGGER.info("User {} excluded all maps.", DiscordUtils.getUserNameAndId(event))
            return
        }

        session.setLastMap(randomMap)

        val playersList = mutableListOf<User>()
        session.getHost()?.let { playersList.add(it) }
        playersList.addAll(session.getPlayers().shuffled())

        val randomCharacters = assignCharactersToPlayers(playersList)

        selectedModes.retainAll(randomMap.modes)
        selectedModes.shuffle()

        val randomMode = selectedModes[Random.nextInt(selectedModes.size)]

        val embedBuilder = EmbedBuilder()
        embedBuilder.setTitle(randomMode.modeName + " on " + randomMap.mapName)
        embedBuilder.setColor(0xab7509)
        embedBuilder.setDescription(getDescription(randomCharacters))

        event.hook.editOriginal("").setEmbeds(embedBuilder.build())
            .setActionRow(
                Button.secondary(REROLL, "Reroll")
                    .withEmoji(Emoji.fromUnicode("\uD83C\uDDF2\uD83C\uDDF3")),
                Button.secondary(REROLL_EXCLUDE, "Exclude map and reroll")
                    .withEmoji(Emoji.fromUnicode("\uD83D\uDC80"))
            )
            .submit()
            .whenComplete { _: Message?, error: Throwable? ->
                if (error != null) {
                    LOGGER.error(
                        "Error while handling rgame sequence from user {}.",
                        DiscordUtils.getUserNameAndId(event),
                        error
                    )
                } else {
                    LOGGER.info(
                        "rgame sequence from user {} handled successfully. Mode: {}, Map: {}",
                        DiscordUtils.getUserNameAndId(event),
                        randomMode.modeName,
                        randomMap.mapName
                    )
                }
            }
    }

    companion object {
        private fun getDescription(randomCharacters: HashMap<User, ru.allexs82.data.Character>): String {
            val descriptionPlants = StringBuilder("Plants team:\n")
            val descriptionZombies = StringBuilder("\nZombies team:\n")
            for ((key, value) in randomCharacters) {
                if (value.side == Sides.PLANTS) {
                    descriptionPlants.append("\n").append(key.asMention).append(" - ").append(value.characterName)
                } else {
                    descriptionZombies.append("\n").append(key.asMention).append(" - ").append(value.characterName)
                }
            }
            descriptionPlants.append("\n")
            return descriptionPlants.append(descriptionZombies).toString()
        }

        private fun assignCharactersToPlayers(players: List<User>): HashMap<User, ru.allexs82.data.Character> {
            val result = HashMap<User, ru.allexs82.data.Character>()
            if (players.size >= 3) {
                assignMultiplePlayers(players, result)
            } else if (players.size == 2) {
                assignTwoPlayers(players, result)
            } else if (players.isNotEmpty()) {
                result[players[0]] = ru.allexs82.data.Character.getRandomCharacter(Sides.ANY)
            }

            return result
        }

        private fun assignMultiplePlayers(players: List<User>, result: HashMap<User, ru.allexs82.data.Character>) {
            var sides = mutableListOf(Sides.PLANTS, Sides.ZOMBIES)
            var zombiesCount = 0
            var plantsCount = 0
            for (player in players) {
                val side = sides[Random.nextInt(sides.size)]
                result[player] = ru.allexs82.data.Character.getRandomCharacter(side)
                if (side == Sides.PLANTS) plantsCount++
                else zombiesCount++
                if (plantsCount == 2) sides = mutableListOf(Sides.ZOMBIES)
                else if (zombiesCount == 2) sides = mutableListOf(Sides.PLANTS)
            }
        }

        private fun assignTwoPlayers(players: List<User>, result: HashMap<User, ru.allexs82.data.Character>) {
            var sides = mutableListOf(Sides.PLANTS, Sides.ZOMBIES)
            for (player in players) {
                val side = sides[Random.nextInt(sides.size)]
                result[player] = ru.allexs82.data.Character.getRandomCharacter(side)
                sides = if (side == Sides.ZOMBIES) mutableListOf(Sides.PLANTS) else mutableListOf(Sides.ZOMBIES)
            }
        }

        private class Session(private var host: User?, private val players: List<User>) {
            private var selectedModesMask = 0
            private var excludedMapsMask = 0
            private var lastMap: GameMap? = null

            fun getHost(): User? {
                return host
            }

            fun getPlayers(): List<User> {
                return players
            }

            fun getSelectedModes(): List<Modes> {
                return EncodingUtils.decodeList(selectedModesMask, Modes.entries.toList())
            }

            fun getExcludedMaps(): List<GameMap> {
                return EncodingUtils.decodeList(excludedMapsMask, GameMap.allMaps)
            }

            fun addSelectedModes(modes: List<Modes>) {
                val selectedModes = getSelectedModes().toMutableList()
                selectedModes.addAll(modes)
                selectedModesMask = EncodingUtils.encodeList(selectedModes, Modes.entries.toList())
            }

            fun excludeAndInvalidateLastMap() {
                val excludedMaps = EncodingUtils.decodeList(excludedMapsMask, GameMap.allMaps)
                if (lastMap != null && !excludedMaps.contains(lastMap)) {
                    excludedMaps.add(lastMap!!)
                    lastMap = null
                }
                excludedMapsMask = EncodingUtils.encodeList(excludedMaps, GameMap.allMaps)
            }

            fun setLastMap(lastMap: GameMap?) {
                this.lastMap = lastMap
            }
        }
    }
}