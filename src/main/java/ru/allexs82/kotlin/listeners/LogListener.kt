package ru.allexs82.kotlin.listeners

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.jetbrains.annotations.NotNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.allexs82.kotlin.utils.DiscordUtils

class LogListener: ListenerAdapter() {
    private val logger: Logger = LoggerFactory.getLogger(LogListener::class.java)

    override fun onSlashCommandInteraction(@NotNull event: SlashCommandInteractionEvent) {
        logger.info("Received slash command {}, from user {}", event.name, DiscordUtils.getUserNameAndId(event))
    }
}