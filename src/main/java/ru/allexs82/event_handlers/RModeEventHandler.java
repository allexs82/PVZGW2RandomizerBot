package ru.allexs82.event_handlers;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.allexs82.Utils;
import ru.allexs82.enums.Maps;
import ru.allexs82.enums.Modes;
import ru.allexs82.exceptions.MapsException;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RModeEventHandler extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RModeEventHandler.class);
    private static final String SLASH_COMMAND_NAME = "rmode";
    private static final String MENU_ID = "rmode_select_modes";
    private static final String REROLL_BUTTON_ID = "rmode_reroll";
    private static final String SELECTED_MODES_MASK_PREFIX = "__selected_modes=";
    private static final String EXCLUDED_MAPS_MASK_PREFIX = "__excluded_maps=";
    private static final Pattern SELECTED_MODES_MASK_REGEX = Pattern.compile("_modes=(\\d+)");
    private static final Pattern EXCLUDED_MAPS_MASK_REGEX = Pattern.compile("_maps=(\\d+)");
    private static final Emoji REROLL_EMOJI = Emoji.fromUnicode("\uD83C\uDDF2\uD83C\uDDF3");
    private static final Emoji EXCLUDE_EMOJI = Emoji.fromUnicode("\uD83D\uDC80");

    private final Random random = new Random();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(SLASH_COMMAND_NAME)) return;

        StringSelectMenu selectMenu = Utils.createModesSelectMenu(MENU_ID);
        event.reply("Select a mode(s):").addActionRow(selectMenu).setEphemeral(true).submit()
                .whenComplete((v, error) -> {
                    if (error != null) {
                        LOGGER.error("Error while handling slash command {} from user {}.", event.getName(), Utils.getUserIdAndName(event), error);
                    } else {
                        LOGGER.info("Handled slash command {} from user {}.", event.getName(), Utils.getUserIdAndName(event));
                    }
                });
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        if (!event.getComponentId().equals(MENU_ID)) return;
        List<Modes> selectedModes = event.getValues().stream()
                .map(Modes::valueOf)
                .collect(Collectors.toCollection(ArrayList::new));

        int selectedModesMask = Utils.encodeSelectedModes(selectedModes);
        sendResponse(event, selectedModesMask, 0);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String componentId = event.getComponentId();
        if (!componentId.startsWith(REROLL_BUTTON_ID)) return;

        int excludedMapsMask = getMaskFromButtonId(event.getComponentId(), EXCLUDED_MAPS_MASK_REGEX);
        int selectedModesMask = getMaskFromButtonId(event.getComponentId(), SELECTED_MODES_MASK_REGEX);
        sendResponse(event, selectedModesMask, excludedMapsMask);
    }

    private void sendResponse(@NotNull GenericComponentInteractionCreateEvent event, int selectedModeMask, int excludedMapsMask) {
        List<Modes> selectedModes = Utils.decodeSelectedModes(selectedModeMask);
        List<Maps> excludedMaps = Utils.decodeExcludedMaps(excludedMapsMask);

        Maps randomMap;
        try {
            randomMap = Maps.getRandomMapForModes(selectedModes, excludedMaps);
        } catch (MapsException e) {
            event.reply("Looks like you are excluded all maps. Please type /rmode again.").setEphemeral(true).queue();
            LOGGER.info("User {} excluded all maps.", Utils.getUserIdAndName(event));
            return;
        }

        selectedModes.retainAll(randomMap.getModes());
        Collections.shuffle(selectedModes, random);
        Modes randomMode = selectedModes.get(random.nextInt(selectedModes.size()));

        String reRollButtonId = REROLL_BUTTON_ID + SELECTED_MODES_MASK_PREFIX + Utils.encodeSelectedModes(selectedModes) + EXCLUDED_MAPS_MASK_PREFIX + excludedMapsMask;
        excludedMaps.add(randomMap);
        String reRollAndExcludeButtonId = REROLL_BUTTON_ID + SELECTED_MODES_MASK_PREFIX + Utils.encodeSelectedModes(selectedModes) + EXCLUDED_MAPS_MASK_PREFIX + Utils.encodeExcludedMaps(excludedMaps);

        event.reply("RNG god says play " + randomMode.getName() + " on " + randomMap.getName() + "!")
                .addActionRow(
                        Button.secondary(reRollButtonId, "Reroll").withEmoji(REROLL_EMOJI),
                        Button.secondary(reRollAndExcludeButtonId, "Exclude map and reroll").withEmoji(EXCLUDE_EMOJI))
                .submit()
                .whenComplete((v, error) -> {
                    if (error != null) {
                        LOGGER.error("Error while handling rmode sequence from user {}.", Utils.getUserIdAndName(event), error);
                    } else {
                        LOGGER.info("rmode sequence from user {} handled successfully. Mode: {}, Map: {}", Utils.getUserIdAndName(event), randomMode.getName(), randomMap.getName());
                    }
                });
    }

    private static int getMaskFromButtonId(String buttonId, @NotNull Pattern pattern) {
        Matcher matcher = pattern.matcher(buttonId);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return 0;
        }
    }
}
