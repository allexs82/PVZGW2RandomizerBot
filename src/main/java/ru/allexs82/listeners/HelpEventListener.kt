package ru.allexs82.listeners

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

private const val COMMAND_NAME = "help"

class HelpEventListener : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != COMMAND_NAME) return
        event.deferReply().queue()

        val embedBuilder = EmbedBuilder()
        embedBuilder.setDescription(
            """
                `/char_packs [count: Int; 1<x<6; Default: 3]` - Sends to you packs with characters. Then you can choose one of theme to get a random character.
                
                `/rmode` - Rolls a random map depending on selected modes.
                
                `/rchar <side: Enum>` - Rolls a random character depending on selected side.
                
                `/rgame [p2: User] [p3: User] [p4: User]` - /rchar and /rmode combined.
                
                `/help` - Show this message.
            """.trimIndent()
        )
        event.hook.deleteOriginal().submit().thenCompose {
            event.hook.sendMessage("").addEmbeds(embedBuilder.build()).submit()
        }
    }
}