package ru.allexs82.kotlin.listeners

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
import ru.allexs82.Utils
import ru.allexs82.event_handlers.RGameEventHandler
import ru.allexs82.kotlin.data.Character
import ru.allexs82.kotlin.data.GameMap
import ru.allexs82.kotlin.data.enums.Modes
import ru.allexs82.kotlin.data.enums.Sides
import ru.allexs82.kotlin.utils.DiscordUtils
import ru.allexs82.kotlin.utils.EncodeUtils
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors
import kotlin.random.Random

private val LOGGER = LoggerFactory.getLogger(RGameEventHandler::class.java)
private const val REROLL = "rgame_reroll"
private const val REROLL_EXCLUDE = "rgame_reroll_exclude"
private const val SELECT_MODES = "rgame_select_modes"
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

        val stringSelectMenu = DiscordUtils.createModesSelectMenu(SELECT_MODES)

        event.hook.deleteOriginal().submit().thenCompose {
            event.hook
                .sendMessage("Select mode(s)")
                .setActionRow(stringSelectMenu)
                .setEphemeral(true)
                .submit()
        }
    }

    private fun getPlayersFromEvent(event: SlashCommandInteractionEvent): MutableList<User> {
        val players = mutableListOf<User>()
        for (i in 2..4) {
            val user = event.getOption("p$i")?.asUser
            if (user != null) {
                players.add(user)
            }
        }
        return players
    }

    override fun onStringSelectInteraction(event: StringSelectInteractionEvent) {
        if (event.componentId != RGameEventHandler.getMenuId()) return
        event.deferReply().queue()

        val userId = event.user.id
        val session = sessionsHashMap[userId]
        if (session == null) {
            event.hook.editOriginal("Oops... Couldn't find your session :confused:. Make sure it was you who sent /rgame.")
                .submit()
                .whenComplete { _: Message?, _: Throwable? ->
                    LOGGER.error("Can't find session for user {}", Utils.getUserIdAndName(event))
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
        val userId = event.user.id
        val session = sessionsHashMap[userId]
        if (session == null) {
            event.hook.deleteOriginal().submit().thenCompose {
                event.hook.editOriginal("Session not found. Please type /rgame again.").submit()
            }
            LOGGER.error("No session found for user {}", Utils.getUserIdAndName(event))
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

            LOGGER.info("User {} excluded all maps.", Utils.getUserIdAndName(event))
            return
        }

        session.setLastMap(randomMap)

        val playersList = mutableListOf<User>()
        session.getHost()?.let { playersList.add(it) }
        playersList.addAll(session.getPlayers())

        val randomCharacters = assignCharactersToPlayers(playersList)

        selectedModes.retainAll(randomMap.modes)
        selectedModes.shuffle()

        val randomMode = selectedModes[Random.nextInt(selectedModes.size)]

        val embedBuilder = EmbedBuilder()
        embedBuilder.setTitle(randomMode.name + " on " + randomMap.name)
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
            .whenComplete { v: Message?, error: Throwable? ->
                if (error != null) {
                    LOGGER.error(
                        "Error while handling rgame sequence from user {}.",
                        Utils.getUserIdAndName(event),
                        error
                    )
                } else {
                    LOGGER.info(
                        "rgame sequence from user {} handled successfully. Mode: {}, Map: {}",
                        Utils.getUserIdAndName(event),
                        randomMode.name,
                        randomMap.name
                    )
                }
            }
    }

    companion object {
        private fun getDescription(randomCharacters: HashMap<User, Character>): String {
            val descriptionPlants = StringBuilder("Plants team:\n")
            val descriptionZombies = StringBuilder("\nZombies team:\n")
            for ((key, value) in randomCharacters) {
                if (value.side == Sides.PLANTS) {
                    descriptionPlants.append("\n").append(key.asMention).append(" - ").append(value.name)
                } else {
                    descriptionZombies.append("\n").append(key.asMention).append(" - ").append(value.name)
                }
            }
            descriptionPlants.append("\n")
            return descriptionPlants.append(descriptionZombies).toString()
        }

        private fun assignCharactersToPlayers(players: List<User>): HashMap<User, Character> {
            val result = HashMap<User, Character>()
            if (players.size >= 3) {
                assignMultiplePlayers(players, result)
            } else if (players.size == 2) {
                assignTwoPlayers(players, result)
            } else if (players.isNotEmpty()) {
                result[players[0]] = Character.getRandomCharacterForSide(Sides.ANY)
            }

            return result
        }

        private fun assignMultiplePlayers(players: List<User>, result: HashMap<User, Character>) {
            var sides = mutableListOf(Sides.PLANTS, Sides.ZOMBIES)
            var zombiesCount = 0
            var plantsCount = 0
            for (player in players) {
                val side = sides[Random.nextInt(sides.size)]
                result[player] = Character.getRandomCharacterForSide(side)
                if (side == Sides.PLANTS) plantsCount++
                else zombiesCount++
                if (plantsCount == 2) sides = mutableListOf(Sides.ZOMBIES)
                else if (zombiesCount == 2) sides = mutableListOf(Sides.PLANTS)
            }
        }

        private fun assignTwoPlayers(players: List<User>, result: HashMap<User, Character>) {
            var sides = mutableListOf(Sides.PLANTS, Sides.ZOMBIES)
            for (player in players) {
                val side = sides[Random.nextInt(sides.size)]
                result[player] = Character.getRandomCharacterForSide(side)
                sides = if (side == Sides.ZOMBIES) mutableListOf(Sides.PLANTS) else mutableListOf(Sides.ZOMBIES)
            }
        }

        private class Session(private var host: User?, players: MutableList<User>) {
            private val players: MutableList<User> = mutableListOf()
            private var selectedModesMask = 0
            private var excludedMapsMask = 0
            private var lastMap: GameMap? = null

            init {
                players.removeIf { obj: User? ->
                    Objects.isNull(
                        obj
                    )
                }
            }

            fun getHost(): User? {
                return host
            }

            fun getPlayers(): List<User> {
                return players.toList()
            }

            fun getSelectedModes(): List<Modes> {
                return EncodeUtils.decodeList(selectedModesMask, Modes.entries.toList())
            }

            fun getExcludedMaps(): List<GameMap> {
                return EncodeUtils.decodeList(excludedMapsMask, GameMap.allMaps)
            }

            fun addSelectedModes(modes: List<Modes>) {
                val selectedModes = getSelectedModes().toMutableList()
                selectedModes.addAll(modes)
                selectedModesMask = EncodeUtils.encodeList(selectedModes, Modes.entries.toList())
            }

            fun excludeAndInvalidateLastMap() {
                val excludedMaps = EncodeUtils.decodeList(excludedMapsMask, GameMap.allMaps)
                if (lastMap != null && !excludedMaps.contains(lastMap)) {
                    excludedMaps.add(lastMap!!)
                    lastMap = null
                }
                excludedMapsMask = EncodeUtils.encodeList(excludedMaps, GameMap.allMaps)
            }

            fun setLastMap(lastMap: GameMap?) {
                this.lastMap = lastMap
            }
        }
    }
}