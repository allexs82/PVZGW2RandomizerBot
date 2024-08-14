package ru.allexs82.deprecated.event_handlers;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.allexs82.deprecated.Utils;
import ru.allexs82.deprecated.enums.Maps;
import ru.allexs82.deprecated.enums.ModesOld;
import ru.allexs82.exceptions.MapsException;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Deprecated
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

        event.deferReply().queue();

        StringSelectMenu selectMenu = Utils.createModesSelectMenu(MENU_ID);
        event.getHook().deleteOriginal().submit().thenCompose(
                (v) ->  event.getHook().sendMessage("Select a mode(s):").addActionRow(selectMenu).setEphemeral(true).submit()
                        .whenComplete((m, error) -> {
                            if (error != null) {
                                LOGGER.error("Error while handling slash command {} from user {}.", event.getName(), Utils.getUserIdAndName(event), error);
                            } else {
                                LOGGER.info("Handled slash command {} from user {}.", event.getName(), Utils.getUserIdAndName(event));
                            }
                        })
        );
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        if (!event.getComponentId().equals(MENU_ID)) return;
        event.deferReply().queue();
        List<ModesOld> selectedModes = event.getValues().stream()
                .map(ModesOld::valueOf)
                .collect(Collectors.toCollection(ArrayList::new));

        int selectedModesMask = Utils.encodeEnums(selectedModes);
        sendResponse(event, selectedModesMask, 0);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String componentId = event.getComponentId();
        if (!componentId.startsWith(REROLL_BUTTON_ID)) return;

        event.deferReply().queue();

        int excludedMapsMask = getMaskFromButtonId(event.getComponentId(), EXCLUDED_MAPS_MASK_REGEX);
        int selectedModesMask = getMaskFromButtonId(event.getComponentId(), SELECTED_MODES_MASK_REGEX);
        sendResponse(event, selectedModesMask, excludedMapsMask);
    }

    private void sendResponse(@NotNull GenericComponentInteractionCreateEvent event, int selectedModesMask, int excludedMapsMask) {
        List<ModesOld> selectedModes = Utils.decodeEnums(selectedModesMask, ModesOld.class);
        List<Maps> excludedMaps = Utils.decodeEnums(excludedMapsMask, Maps.class);

        Maps randomMap;
        try {
            randomMap = Maps.getRandomMapForModes(selectedModes, excludedMaps);
        } catch (MapsException e) {
            event.getHook().deleteOriginal().submit().thenCompose(
                    v -> event.getHook().sendMessage("Looks like you are excluded all maps. Please type /rmode again.").setEphemeral(true).submit());
            LOGGER.info("User {} excluded all maps.", Utils.getUserIdAndName(event));
            return;
        }

        selectedModes.retainAll(randomMap.getModes());
        Collections.shuffle(selectedModes, random);
        ModesOld randomMode = selectedModes.get(random.nextInt(selectedModes.size()));

        String reRollButtonId = REROLL_BUTTON_ID + SELECTED_MODES_MASK_PREFIX + selectedModesMask + EXCLUDED_MAPS_MASK_PREFIX + excludedMapsMask;
        excludedMaps.add(randomMap);
        String reRollAndExcludeButtonId = REROLL_BUTTON_ID + SELECTED_MODES_MASK_PREFIX + selectedModesMask + EXCLUDED_MAPS_MASK_PREFIX + Utils.encodeEnums(excludedMaps);

        event.getHook().editOriginal("RNG god says play " + randomMode.getName() + " on " + randomMap.getName() + "!")
                .setActionRow(
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
