package ru.allexs82.listeners

import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button
import org.slf4j.LoggerFactory
import ru.allexs82.data.enums.Sides
import ru.allexs82.utils.DiscordUtils
import java.util.*

private val LOGGER = LoggerFactory.getLogger(RCharEventListener::class.java)
private const val SLASH_COMMAND_NAME = "rchar"
private const val REROLL_BUTTON_ID = "rchar_reroll__side="
private val REROLL_EMOJI: Emoji = Emoji.fromUnicode("\uD83C\uDDF2\uD83C\uDDF3")

class RCharEventListener : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != SLASH_COMMAND_NAME) return
        event.deferReply().queue()

        val side = event.getOption("side")?.asString?.let { Sides.valueOf(it.uppercase(Locale.getDefault())) }
        if (side == null) {
            event.hook.deleteOriginal()
            event.hook.sendMessage("Invalid side option. Please choose between PLANTS, ZOMBIES, or ANY.")
                .setEphemeral(true).queue()
            LOGGER.error("Invalid side option. User: {}", DiscordUtils.getUserNameAndId(event))
            return
        }

        sendCharacterReply(event, side)
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        if (!event.componentId.startsWith(REROLL_BUTTON_ID)) return
        event.deferReply().queue()

        val side = Sides.valueOf(event.componentId.substring(REROLL_BUTTON_ID.length).uppercase(Locale.getDefault()))
        sendCharacterReply(event, side)
    }

    private fun sendCharacterReply(event: SlashCommandInteractionEvent, side: Sides) {
        val selectedCharacter = ru.allexs82.data.Character.getRandomCharacter(side)

        event.hook.editOriginal("RNG god thinks that your character is: " + selectedCharacter.characterName)
            .setActionRow(
                Button.secondary(
                    REROLL_BUTTON_ID + side.name.lowercase(Locale.getDefault()),
                    "Reroll"
                ).withEmoji(REROLL_EMOJI)
            ).queue()
        LOGGER.info(
            "User {} got character: {}",
            DiscordUtils.getUserNameAndId(event),
            selectedCharacter.characterName
        )
    }

    private fun sendCharacterReply(event: ButtonInteractionEvent, side: Sides) {
        val selectedCharacter = ru.allexs82.data.Character.getRandomCharacter(side)

        event.hook.editOriginal("RNG god thinks that your character is: " + selectedCharacter.characterName)
            .setActionRow(
                Button.secondary(
                    REROLL_BUTTON_ID + side.name.lowercase(Locale.getDefault()),
                    "Reroll"
                ).withEmoji(REROLL_EMOJI)
            ).queue()
        LOGGER.info(
            "User {} got character: {}. (handleButtonInteraction)",
            DiscordUtils.getUserNameAndId(event),
            selectedCharacter.characterName
        )
    }
}