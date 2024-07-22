package ru.allexs82.event_handlers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class HelpEventHandler extends ListenerAdapter {
    private static final String COMMAND_NAME = "help";
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(COMMAND_NAME)) return;
        event.deferReply().queue();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription(
                "`/char_packs [count: Int; 1<x<6; Default: 3]` - Sends to you packs with characters. Then you can choose one of theme to get a random character\n\n" +
                "`/rmode` - Rolls a random map depending on selected modes\n\n" +
                "`/rchar <side: Enum>` - Rolls a random character depending on selected side\n\n" +
                "`/rgame [p2: User] [p3: User] [p4: User]` - /rchar and /rmode combined\n\n" +
                "`/help` - Show this message"
        );
        event.getHook().deleteOriginal().submit().thenCompose(
                (v) -> event.getHook().sendMessage("").addEmbeds(embedBuilder.build()).submit()
        );
    }
}