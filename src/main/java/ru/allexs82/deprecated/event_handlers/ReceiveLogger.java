package ru.allexs82.deprecated.event_handlers;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.allexs82.deprecated.Utils;

@Deprecated
public class ReceiveLogger extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiveLogger.class);

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        LOGGER.info("Received slash command \"{}\", from user {}.", event.getName(), Utils.getUserIdAndName(event));
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        LOGGER.info("Received string select menu \"{}\", from user {}.", event.getComponentId(), Utils.getUserIdAndName(event));
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        LOGGER.info("Received button interaction \"{}\", from user {}.", event.getComponentId(), Utils.getUserIdAndName(event));
    }
}
