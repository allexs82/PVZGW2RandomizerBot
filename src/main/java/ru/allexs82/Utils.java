package ru.allexs82;

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
     * Encodes a list of enums into a bitwise mask.
     *
     * @param <E>   the enum type
     * @param enums the list of enums to encode
     * @return a bitwise mask representing the enums
     */
    public static <E extends Enum<E>> int encodeEnums(@NotNull List<E> enums) {
        int mask = 0;
        for (E e : enums) {
            mask |= 1 << e.ordinal();
        }
        return mask;
    }

    /**
     * Decodes a bitwise mask to retrieve the list of enums.
     *
     * @param mask      the bitwise mask representing the enums
     * @param enumClass the enum class
     * @param <E>       the enum type
     * @return a list of enums that are represented by the mask
     */
    @NotNull
    public static <E extends Enum<E>> List<E> decodeEnums(int mask, @NotNull Class<E> enumClass) {
        List<E> enums = new ArrayList<>();
        for (E e : enumClass.getEnumConstants()) {
            if ((mask & (1 << e.ordinal())) != 0) {
                enums.add(e);
            }
        }
        return enums;
    }
}
