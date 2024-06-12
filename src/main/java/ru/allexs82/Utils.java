package ru.allexs82;

import ru.allexs82.enums.Maps;
import ru.allexs82.enums.Modes;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for common methods used across the application.
 */
public abstract class Utils {

    /**
     * Returns the user ID and name in the format "ID (Name)".
     *
     * @param event the interaction event
     * @return a string containing the user ID and name
     */
    @NotNull
    public static String getUserIdAndName(@NotNull GenericInteractionCreateEvent event) {
        event.getUser();
        return event.getUser().getId() + " (" + event.getUser().getName() + ")";
    }

    /**
     * Creates a StringSelectMenu for selecting modes.
     *
     * @param id the ID of the menu
     * @return a StringSelectMenu populated with the available modes
     */
    @NotNull
    public static StringSelectMenu createModesSelectMenu(@NotNull String id) {
        StringSelectMenu.Builder menuBuilder = StringSelectMenu.create(id);
        for (Modes mode : Modes.values()) {
            menuBuilder.addOption(mode.getName(), mode.name());
        }
        return menuBuilder.setMinValues(1).setMaxValues(Modes.values().length).build();
    }

    /**
     * Encodes a list of excluded maps into a bitwise mask.
     *
     * @param excludedMaps the list of maps to be excluded
     * @return a bitwise mask representing the excluded maps
     */
    public static int encodeExcludedMaps(@NotNull List<Maps> excludedMaps) {
        int mask = 0;
        for (Maps map : Maps.values()) {
            if (excludedMaps.contains(map)) {
                mask |= 1 << map.ordinal();
            }
        }
        return mask;
    }

    /**
     * Decodes a bitwise mask to retrieve the list of excluded maps.
     *
     * @param mask the bitwise mask representing the excluded maps
     * @return a list of maps that are excluded
     */
    @NotNull
    public static List<Maps> decodeExcludedMaps(int mask) {
        List<Maps> excludedMaps = new ArrayList<>();
        for (Maps map : Maps.values()) {
            if ((mask & (1 << map.ordinal())) != 0) {
                excludedMaps.add(map);
            }
        }
        return excludedMaps;
    }

    /**
     * Encodes a list of selected modes into a bitwise mask.
     *
     * @param selectedModes the list of selected modes
     * @return a bitwise mask representing the selected modes
     */
    public static int encodeSelectedModes(@NotNull List<Modes> selectedModes) {
        int mask = 0;
        for (Modes mode : Modes.values()) {
            if (selectedModes.contains(mode)) {
                mask |= 1 << mode.ordinal();
            }
        }
        return mask;
    }

    /**
     * Decodes a bitwise mask to retrieve the list of excluded maps.
     *
     * @param mask the bitwise mask representing the selected modes
     * @return a list of selected modes.
     */
    @NotNull
    public static List<Modes> decodeSelectedModes(int mask) {
        List<Modes> selectedModes = new ArrayList<>();
        for (Modes mode : Modes.values()) {
            if ((mask & (1 << mode.ordinal())) != 0) {
                selectedModes.add(mode);
            }
        }
        return selectedModes;
    }
}
