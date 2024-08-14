package ru.allexs82.listeners

import net.dv8tion.jda.api.entities.Message
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
import ru.allexs82.utils.DiscordUtils
import ru.allexs82.utils.EncodingUtils
import java.util.regex.Pattern
import java.util.stream.Collectors
import kotlin.random.Random

private val LOGGER = LoggerFactory.getLogger(RModeEventListener::class.java)
private const val SLASH_COMMAND_NAME = "rmode"
private const val MENU_ID = "rmode_select_modes"
private const val REROLL_BUTTON_ID = "rmode_reroll"
private const val SELECTED_MODES_MASK_PREFIX = "__selected_modes="
private const val EXCLUDED_MAPS_MASK_PREFIX = "__excluded_maps="
private val SELECTED_MODES_MASK_REGEX = Pattern.compile("_modes=(\\d+)")
private val EXCLUDED_MAPS_MASK_REGEX = Pattern.compile("_maps=(\\d+)")
private val REROLL_EMOJI: Emoji = Emoji.fromUnicode("\uD83C\uDDF2\uD83C\uDDF3")
private val EXCLUDE_EMOJI: Emoji = Emoji.fromUnicode("\uD83D\uDC80")

class RModeEventListener : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != SLASH_COMMAND_NAME) return

        event.deferReply().queue()

        val selectMenu = DiscordUtils.createModesSelectMenu(MENU_ID)
        event.hook.deleteOriginal().submit().thenCompose {
            event.hook.sendMessage("Select a mode(s):").addActionRow(selectMenu).setEphemeral(true).submit()
                .whenComplete { _: Message?, error: Throwable? ->
                    if (error != null) {
                        LOGGER.error(
                            "Error while handling slash command {} from user {}.",
                            event.name,
                            DiscordUtils.getUserNameAndId(event),
                            error
                        )
                    } else {
                        LOGGER.info(
                            "Handled slash command {} from user {}.",
                            event.name,
                            DiscordUtils.getUserNameAndId(event)
                        )
                    }
                }
        }
    }

    override fun onStringSelectInteraction(event: StringSelectInteractionEvent) {
        if (event.componentId != MENU_ID) return
        event.deferReply().queue()
        val selectedModes: List<Modes> = event.values.stream()
            .map { name: String? -> Modes.valueOf(name!!) }
            .collect(
                Collectors.toCollection { ArrayList() }
            )

        val selectedModesMask = EncodingUtils.encodeList(selectedModes, Modes.entries)
        sendResponse(event, selectedModesMask, 0)
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        val componentId = event.componentId
        if (!componentId.startsWith(REROLL_BUTTON_ID)) return

        event.deferReply().queue()

        val excludedMapsMask = getMaskFromButtonId(event.componentId, EXCLUDED_MAPS_MASK_REGEX)
        val selectedModesMask = getMaskFromButtonId(event.componentId, SELECTED_MODES_MASK_REGEX)
        sendResponse(event, selectedModesMask, excludedMapsMask)
    }

    private fun sendResponse(event: GenericComponentInteractionCreateEvent, selectedModesMask: Int, excludedMapsMask: Int) {
        val selectedModes = EncodingUtils.decodeList(
            selectedModesMask,
            Modes.entries
        )
        val excludedMaps = EncodingUtils.decodeList(
            excludedMapsMask,
            GameMap.allMaps
        )

        val randomMap = GameMap.getRandomMapForModes(selectedModes, excludedMaps)
        if (randomMap == null) {
            event.hook.deleteOriginal().submit().thenCompose {
                event.hook.sendMessage("Looks like you are excluded all maps. Please type /rmode again.")
                    .setEphemeral(true).submit()
            }
            LOGGER.info("User {} excluded all maps.", DiscordUtils.getUserNameAndId(event))
            return
        }

        selectedModes.retainAll(randomMap.modes)
        selectedModes.shuffle()
        val randomMode = selectedModes[Random.nextInt(selectedModes.size)]

        val reRollButtonId =
            REROLL_BUTTON_ID + SELECTED_MODES_MASK_PREFIX + selectedModesMask + EXCLUDED_MAPS_MASK_PREFIX + excludedMapsMask
        excludedMaps.add(randomMap)
        val reRollAndExcludeButtonId =
            REROLL_BUTTON_ID + SELECTED_MODES_MASK_PREFIX + selectedModesMask + EXCLUDED_MAPS_MASK_PREFIX + EncodingUtils.encodeList(
                excludedMaps, GameMap.allMaps
            )

        event.hook.editOriginal("RNG god says play " + randomMode.modeName + " on " + randomMap.mapName+ "!")
            .setActionRow(
                Button.secondary(reRollButtonId, "Reroll").withEmoji(REROLL_EMOJI),
                Button.secondary(reRollAndExcludeButtonId, "Exclude map and reroll")
                    .withEmoji(EXCLUDE_EMOJI)
            )
            .submit()
            .whenComplete { _: Message?, error: Throwable? ->
                if (error != null) {
                    LOGGER.error(
                        "Error while handling rmode sequence from user {}.",
                        DiscordUtils.getUserNameAndId(event),
                        error
                    )
                } else {
                    LOGGER.info(
                        "rmode sequence from user {} handled successfully. Mode: {}, Map: {}",
                        DiscordUtils.getUserNameAndId(event),
                        randomMode.modeName,
                        randomMap.mapName
                    )
                }
            }
    }

    private fun getMaskFromButtonId(buttonId: String, pattern: Pattern): Int {
        val matcher = pattern.matcher(buttonId)

        return if (matcher.find()) {
            matcher.group(1).toInt()
        } else {
            0
        }
    }
}