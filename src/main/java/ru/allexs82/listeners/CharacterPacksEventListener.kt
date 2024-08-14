package ru.allexs82.listeners

import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.ItemComponent
import net.dv8tion.jda.api.interactions.components.buttons.Button
import org.slf4j.LoggerFactory
import ru.allexs82.data.CharacterPacks
import ru.allexs82.utils.DiscordUtils
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

private val LOGGER = LoggerFactory.getLogger(CharacterPacksEventListener::class.java)
private const val MIN_PACK_COUNT = 2
private const val DEFAULT_PACK_COUNT = 3
private const val MAX_PACK_COUNT = 5
private const val BUTTON_PREFIX = "char_packs_pack_"

class CharacterPacksEventListener : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != "char_packs") return
        event.deferReply().queue()

        var count = DEFAULT_PACK_COUNT

        val countOption = event.getOption("count")?.asInt
        if (countOption != null) {
            count = max(MIN_PACK_COUNT, min(MAX_PACK_COUNT, countOption))
        }
        val charPacks: List<CharacterPacks> = getRandomPacks(count)

        val embeds: MutableList<MessageEmbed> = ArrayList()
        val actionRow: MutableList<ItemComponent> = ArrayList()
        for (pack in charPacks) {
            embeds.add(pack.toEmbed())
            actionRow.add(
                Button.secondary(
                    BUTTON_PREFIX + CharacterPacks.allPacks.indexOf(pack),
                    "Pack " + embeds.size
                )
            )
        }
        LOGGER.info("char_packs slash command from user {} handled successfully", DiscordUtils.getUserNameAndId(event))
        event.hook.editOriginal("Select the pack").setEmbeds(embeds).setActionRow(actionRow).queue()
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        if (!event.componentId.startsWith(BUTTON_PREFIX)) return
        event.deferReply().queue()

        val selectedPack: CharacterPacks
        try {
            selectedPack = CharacterPacks.allPacks[event.componentId.substring(BUTTON_PREFIX.length).toInt()]
        } catch (e: NumberFormatException) {
            event.hook.deleteOriginal()
            LOGGER.error("Invalid pack selected by user {}: {}", DiscordUtils.getUserNameAndId(event), event.componentId.substring(BUTTON_PREFIX.length))
            event.hook.sendMessage("Invalid pack selection. Please try again.").setEphemeral(true).queue()
            return
        }

        val character = selectedPack.characters[Random.nextInt(selectedPack.characters.size)]

        event.hook.editOriginal(event.user.asMention + " your character is " + character.characterName).queue()
        LOGGER.info("Button interaction {} from user {} handled successfully. Character: {}", event.componentId, DiscordUtils.getUserNameAndId(event), character.characterName)
    }

    private fun getRandomPacks(count: Int): List<CharacterPacks> {
        require(count > 0) { "Count must be positive" }

        val charPacks = CharacterPacks.allPacks.toMutableList()
        charPacks.shuffle()
        return charPacks.subList(0, min(count, charPacks.size))
    }
}