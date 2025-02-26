package ru.allexs82.utils

import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import ru.allexs82.JDAManager

object CommandSetupUtil {
    fun setupSlashCommands() {
        JDAManager.getJdaInstance().updateCommands().addCommands(
            Commands.slash("char_packs", "Roll a character with selected pack")
                .addOption(OptionType.NUMBER, "count", "1<count<6"),

            Commands.slash("help", "Shows help"),

            Commands.slash("rchar", "Roll a random character")
                .addOptions(OptionData(OptionType.STRING, "side", "Side")
                    .addChoice("Plants", "PLANTS")
                    .addChoice("Zombies", "ZOMBIES")
                    .addChoice("Any", "ANY")),

            Commands.slash("rgame", "Roll a game setup")
                .addOption(OptionType.USER, "p2", "Second player")
                .addOption(OptionType.USER, "p3", "Third player")
                .addOption(OptionType.USER, "p4", "Fourth player"),

            Commands.slash("rmode", "Roll a map for specified mode")
        )
    }
}