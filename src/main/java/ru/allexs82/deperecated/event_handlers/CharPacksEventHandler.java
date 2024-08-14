package ru.allexs82.deperecated.event_handlers;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.allexs82.deperecated.Utils;
import ru.allexs82.deperecated.enums.CharPacks;
import ru.allexs82.deperecated.enums.Characters;

import java.util.*;

/**
 * Event handler for the /char_packs command and associated button interactions.
 */
@Deprecated
public class CharPacksEventHandler extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharPacksEventHandler.class);
    private static final int MIN_PACK_COUNT = 2;
    private static final int DEFAULT_PACK_COUNT = 3;
    private static final int MAX_PACK_COUNT = 5;
    private static final String BUTTON_PREFIX = "char_packs_pack_";
    private final Random random = new Random();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("char_packs")) return;
        event.deferReply().queue();

        int count = DEFAULT_PACK_COUNT;
        try {
            int countOption = Objects.requireNonNull(event.getOption("count")).getAsInt();
            count = Math.max(MIN_PACK_COUNT, Math.min(MAX_PACK_COUNT, countOption));
        } catch (NullPointerException ignored) {}

        List<CharPacks> charPacks = getRandomPacks(count);

        List<MessageEmbed> embeds = new ArrayList<>();
        List<ItemComponent> actionRow = new ArrayList<>();
        for (CharPacks pack : charPacks) {
            embeds.add(pack.toEmbed());
            actionRow.add(Button.secondary(BUTTON_PREFIX + pack.name().toLowerCase(), "Pack " + embeds.size()));
        }

        LOGGER.info("char_packs slash command from user {} handled successfully", Utils.getUserIdAndName(event));
        event.getHook().editOriginal("Select the pack").setEmbeds(embeds).setActionRow(actionRow).queue();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if (!event.getComponentId().startsWith(BUTTON_PREFIX)) return;
        event.deferReply().queue();

        CharPacks selectedPack;
        try {
            selectedPack = CharPacks.valueOf(event.getComponentId().substring(BUTTON_PREFIX.length()).toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid pack selected by user {}: {}", Utils.getUserIdAndName(event), event.getComponentId().substring(BUTTON_PREFIX.length()));
            event.reply("Invalid pack selection. Please try again.").setEphemeral(true).queue();
            return;
        }

        Characters character = selectedPack.getCharacters().get(random.nextInt(selectedPack.getCharacters().size()));

        event.getHook().editOriginal(event.getUser().getAsMention() + " your character is " + character.getName()).queue();
        LOGGER.info("Button interaction {} from user {} handled successfully. Character: {}", event.getComponentId(), Utils.getUserIdAndName(event), character.getName());
    }

    @NotNull
    private List<CharPacks> getRandomPacks(int count) {
        if (count <= 0) throw new IllegalArgumentException("Count must be positive");

        List<CharPacks> charPacks = Arrays.asList(CharPacks.values());
        Collections.shuffle(charPacks, random);
        return Collections.unmodifiableList(new ArrayList<>(charPacks.subList(0, Math.min(count, charPacks.size()))));
    }
}
