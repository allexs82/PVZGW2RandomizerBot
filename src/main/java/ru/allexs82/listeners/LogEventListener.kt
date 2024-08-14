package ru.allexs82.listeners

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.jetbrains.annotations.NotNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.allexs82.utils.DiscordUtils

class LogEventListener: ListenerAdapter() {
    private val logger: Logger = LoggerFactory.getLogger(LogEventListener::class.java)

    override fun onSlashCommandInteraction(@NotNull event: SlashCommandInteractionEvent) {
        logger.info("Received slash command \"{}\", from user {}", event.name, DiscordUtils.getUserNameAndId(event))
    }

    override fun onStringSelectInteraction(event: StringSelectInteractionEvent) {
        logger.info("Received string select menu \"{}\", from user {}.", event.componentId, DiscordUtils.getUserNameAndId(event))
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        logger.info("Received button interaction \"{}\", from user {}.", event.componentId, DiscordUtils.getUserNameAndId(event))
    }
}