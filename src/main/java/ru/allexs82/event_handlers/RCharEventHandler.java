package ru.allexs82.event_handlers;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.allexs82.Utils;
import ru.allexs82.enums.Characters;
import ru.allexs82.enums.Sides;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RCharEventHandler extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RCharEventHandler.class);
    private static final String SLASH_COMMAND_NAME = "rchar";
    private static final String REROLL_BUTTON_ID = "rchar-reroll";
    private static final Emoji REROLL_EMOJI = Emoji.fromUnicode("\uD83C\uDDF2\uD83C\uDDF3");

    private final Map<String, Sides> temporalMemorySide = new ConcurrentHashMap<>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(SLASH_COMMAND_NAME) || event.getUser().isBot()) return;

        temporalMemorySide.remove(event.getUser().getId());
        Sides side;
        try {
            side = Sides.valueOf(Objects.requireNonNull(event.getOption("side")).getAsString().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            event.reply("Invalid side option. Please choose between PLANTS, ZOMBIES, or ANY.").queue();
            LOGGER.error("Invalid side option. Please choose between PLANTS, ZOMBIES, or ANY.", e);
            return;
        }

        temporalMemorySide.put(event.getUser().getId(), side);
        sendCharacterReply(event, side);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getComponentId().equals(REROLL_BUTTON_ID)) return;

        Sides side = temporalMemorySide.getOrDefault(event.getUser().getId(), Sides.ANY);
        sendCharacterReply(event, side);
    }

    private void sendCharacterReply(@NotNull SlashCommandInteractionEvent event, Sides side) {
        Characters selectedCharacter = Characters.getRandomCharacter(side);

        event.reply("RNG god thinks that your character is: " + selectedCharacter.getName())
                .addActionRow(Button.secondary(REROLL_BUTTON_ID, "Reroll").withEmoji(REROLL_EMOJI))
                .queue();
        LOGGER.info("User {} got character: {}", Utils.getUserIdAndName(event), selectedCharacter.getName());
    }

    private void sendCharacterReply(@NotNull ButtonInteractionEvent event, Sides side) {
        Characters selectedCharacter = Characters.getRandomCharacter(side);

        event.reply("RNG god thinks that your character is: " + selectedCharacter.getName())
                .addActionRow(Button.secondary(REROLL_BUTTON_ID, "Reroll").withEmoji(REROLL_EMOJI))
                .queue();
        LOGGER.info("User {} got character: {}. (handleButtonInteraction)", Utils.getUserIdAndName(event), selectedCharacter.getName());
    }
}
