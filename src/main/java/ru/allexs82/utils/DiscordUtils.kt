package ru.allexs82.utils

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu
import org.jetbrains.annotations.NotNull
import ru.allexs82.data.enums.Modes

object DiscordUtils {
    /**
     * Returns the user ID and name in the format "Name (ID)".
     *
     * @param event the interaction event
     * @return string containing name and the user ID
     */
    fun getUserNameAndId(@NotNull event: GenericInteractionCreateEvent): String {
        return event.user.name + " (" + event.user.id + ")"
    }

    /**
     * Creates a StringSelectMenu for selecting modes.
     *
     * @param id the ID of the menu
     * @return [StringSelectMenu] populated with the available modes
     */
    fun createModesSelectMenu(id: String): StringSelectMenu {
        val menuBuilder = StringSelectMenu.create(id)
        for (mode in Modes.entries) {
            menuBuilder.addOption(mode.modeName, mode.name)
        }
        return menuBuilder.setMinValues(1).setMaxValues(Modes.entries.size).build()
    }
}