package ru.allexs82.deperecated.event_handlers;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.Contract;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.allexs82.deperecated.Utils;
import ru.allexs82.deperecated.enums.Characters;
import ru.allexs82.deperecated.enums.Maps;
import ru.allexs82.deperecated.enums.ModesOld;
import ru.allexs82.deperecated.enums.Sides;
import ru.allexs82.exceptions.MapsException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Deprecated
public class RGameEventHandler extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RGameEventHandler.class);
    private static final String REROLL = "rgame_reroll";
    private static final String REROLL_EXCLUDE = "rgame_reroll_exclude";
    private static final String SELECT_MODES = "rgame_select_modes";
    private static final String SLASH_COMMAND_NAME = "rgame";
    private final Random random = new Random();
    private final ConcurrentHashMap<String, Session> sessionsHashMap = new ConcurrentHashMap<>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(SLASH_COMMAND_NAME)) return;
        event.deferReply().queue();

        sessionsHashMap.remove(event.getUser().getId());
        List<User> players = getPlayersFromEvent(event);

        Session session = new Session(event.getUser(), players);
        sessionsHashMap.put(event.getUser().getId(), session);

        StringSelectMenu stringSelectMenu = Utils.createModesSelectMenu(getMenuId());

        event.getHook().deleteOriginal().submit()
                .thenCompose(
                        (v) -> event.getHook().sendMessage("Select mode(s)")
                                .setActionRow(stringSelectMenu)
                                .setEphemeral(true)
                                .submit()
                ).whenComplete((v, error) -> {
            if (error != null) {
                LOGGER.error("Error while handling slash command {} from user {}.", event.getName(), Utils.getUserIdAndName(event), error);
            } else {
                LOGGER.info("Handled slash command {} from user {}.", event.getName(), Utils.getUserIdAndName(event));
            }
        });
    }

    private List<User> getPlayersFromEvent(SlashCommandInteractionEvent event) {
        List<User> players = new ArrayList<>();
        for (int i = 2; i <= 4; ++i) {
            try {
                User user = Objects.requireNonNull(event.getOption("p" + i)).getAsUser();
                players.add(user);
            } catch (NullPointerException ignored) {}
        }
        return players;
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        if (!event.getComponentId().equals(getMenuId())) return;
        event.deferReply().queue();

        String userId = event.getUser().getId();
        Session session = sessionsHashMap.get(userId);
        if (session == null) {
            event.getHook().editOriginal("Oops... Couldn't find your session :confused:. Make sure it was you who sent /rgame.").submit()
                    .whenComplete((v, error) -> LOGGER.error("Can't find session for user {}", Utils.getUserIdAndName(event)));
            return;
        }
        session.addSelectedModes(event.getValues()
                .stream()
                .map(ModesOld::valueOf)
                .collect(Collectors.toCollection(ArrayList::new)));
        sendResponse(event);
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String componentId = event.getComponentId();
        if (!componentId.equals(REROLL) && !componentId.equals(REROLL_EXCLUDE)) return;
        event.deferReply().queue();
        if (componentId.equals(REROLL_EXCLUDE)) {
            sessionsHashMap.get(event.getUser().getId()).excludeAndInvalidateLastMap();
        }
        sendResponse(event);
    }

    private void sendResponse(@NotNull GenericComponentInteractionCreateEvent event) {
        String userId = event.getUser().getId();
        Session session = sessionsHashMap.get(userId);
        if (session == null) {
            event.getHook().deleteOriginal().submit().thenCompose(
                    (v) -> event.getHook().editOriginal("Session not found. Please type /rgame again.").submit()
            );
            LOGGER.error("No session found for user {}", Utils.getUserIdAndName(event));
            return;
        }
        handleSessionResponse(event, session);
    }

    private void handleSessionResponse(@NotNull GenericComponentInteractionCreateEvent event, Session session) {
        List<Maps> excludedMaps = session.getExcludedMaps();
        List<ModesOld> selectedModes = session.getSelectedModes();

        Maps randomMap;
        try {
            randomMap = Maps.getRandomMapForModes(selectedModes, excludedMaps);
        } catch (MapsException e) {
            event.getHook().deleteOriginal().submit().thenCompose(
                    (v) -> event.getHook().sendMessage("Looks like you are excluded all maps. Please type /rgame again.").setEphemeral(true).submit()
            );

            LOGGER.info("User {} excluded all maps.", Utils.getUserIdAndName(event));
            return;
        }

        session.setLastMap(randomMap);

        List<User> playersList = new ArrayList<>();
        playersList.add(session.getHost());
        playersList.addAll(session.getPlayers());

        HashMap<User, Characters> randomCharacters = assignCharactersToPlayers(playersList);

        selectedModes.retainAll(randomMap.getModes());
        Collections.shuffle(selectedModes, random);
        ModesOld randomMode = selectedModes.get(random.nextInt(selectedModes.size()));

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(randomMode.getName() + " on " + randomMap.getName());
        embedBuilder.setColor(0xFF548AF7);
        embedBuilder.setDescription(getDescription(randomCharacters));

        event.getHook().editOriginal("").setEmbeds(embedBuilder.build())
                .setActionRow(
                        Button.secondary(REROLL, "Reroll").withEmoji(Emoji.fromUnicode("\uD83C\uDDF2\uD83C\uDDF3")),
                        Button.secondary(REROLL_EXCLUDE, "Exclude map and reroll").withEmoji(Emoji.fromUnicode("\uD83D\uDC80")))
                .submit()
                .whenComplete((v, error) -> {
                    if (error != null) {
                        LOGGER.error("Error while handling rgame sequence from user {}.", Utils.getUserIdAndName(event), error);
                    } else {
                        LOGGER.info("rgame sequence from user {} handled successfully. Mode: {}, Map: {}", Utils.getUserIdAndName(event), randomMode.getName(), randomMap.getName());
                    }
                });
    }

    @NotNull
    @Contract(pure = true)
    public static String getMenuId() {
        return SELECT_MODES;
    }

    @NotNull
    private static HashMap<User, Characters> assignCharactersToPlayers(@NotNull List<User> players) {
        HashMap<User, Characters> result = new HashMap<>();
        Sides[] sides = {Sides.PLANTS, Sides.ZOMBIES};
        Random random = new Random();

        if (players.size() >= 3) {
            assignMultiplePlayers(players, result, sides, random);
        } else if (players.size() == 2) {
            assignTwoPlayers(players, result, sides, random);
        } else if (!players.isEmpty()) {
            result.put(players.get(0), Characters.getRandomCharacter(Sides.ANY));
        }

        return result;
    }

    private static void assignMultiplePlayers(@NotNull List<User> players, @NotNull HashMap<User, Characters> result, Sides[] sides, Random random) {
        int zombiesCount = 0;
        int plantsCount = 0;
        for (User player : players) {
            Sides side = sides[random.nextInt(sides.length)];
            result.put(player, Characters.getRandomCharacter(side));
            if (side == Sides.PLANTS) plantsCount++;
            else zombiesCount++;
            if (plantsCount == 2) sides = new Sides[]{Sides.ZOMBIES};
            else if (zombiesCount == 2) sides = new Sides[]{Sides.PLANTS};
        }
    }

    private static void assignTwoPlayers(@NotNull List<User> players, @NotNull HashMap<User, Characters> result, Sides[] sides, Random random) {
        for (User player : players) {
            Sides side = sides[random.nextInt(sides.length)];
            result.put(player, Characters.getRandomCharacter(side));
            sides = side == Sides.ZOMBIES ? new Sides[]{Sides.PLANTS} : new Sides[]{Sides.ZOMBIES};
        }
    }

    @NotNull
    private static String getDescription(@NotNull HashMap<User, Characters> randomCharacters) {
        StringBuilder descriptionPlants = new StringBuilder("Plants team:\n");
        StringBuilder descriptionZombies = new StringBuilder("\nZombies team:\n");
        for (Map.Entry<User, Characters> entry : randomCharacters.entrySet()) {
            if (entry.getValue().getSide().equals(Sides.PLANTS)) {
                descriptionPlants.append("\n").append(entry.getKey().getAsMention()).append(" - ").append(entry.getValue().getName());
            } else {
                descriptionZombies.append("\n").append(entry.getKey().getAsMention()).append(" - ").append(entry.getValue().getName());
            }
        }
        descriptionPlants.append("\n");
        return descriptionPlants.append(descriptionZombies).toString();
    }

    private static class Session {
        private final User host;
        private final List<User> players = new ArrayList<>();
        private int selectedModesMask = 0;
        private int excludedMapsMask = 0;
        private Maps lastMap = null;

        Session(User host, @NotNull List<User> players) {
            this.host = host;
            players.removeIf(Objects::isNull);
            this.players.addAll(players);
        }

        public User getHost() {
            return host;
        }

        @NotNull
        @Contract(value = " -> new", pure = true)
        public List<User> getPlayers() {
            return new ArrayList<>(players);
        }

        @NotNull
        @Contract(value = " -> new", pure = true)
        public List<ModesOld> getSelectedModes() {
            return Utils.decodeEnums(selectedModesMask, ModesOld.class);
        }

        @NotNull
        @Contract(value = " -> new", pure = true)
        public List<Maps> getExcludedMaps() {
            return Utils.decodeEnums(excludedMapsMask, Maps.class);
        }

        public void addSelectedModes(@NotNull List<ModesOld> modes) {
            List<ModesOld> selectedModes = Utils.decodeEnums(selectedModesMask, ModesOld.class);
            selectedModes.addAll(modes);
            selectedModesMask = Utils.encodeEnums(selectedModes);
        }

        public void excludeAndInvalidateLastMap() {
            List<Maps> excludedMaps = Utils.decodeEnums(excludedMapsMask, Maps.class);
            if (lastMap != null && !excludedMaps.contains(lastMap)) {
                excludedMaps.add(lastMap);
                lastMap = null;
            }
            excludedMapsMask = Utils.encodeEnums(excludedMaps);
        }

        public void setLastMap(Maps lastMap) {
            this.lastMap = lastMap;
        }
    }
}
