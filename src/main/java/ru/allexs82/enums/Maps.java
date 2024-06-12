package ru.allexs82.enums;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.allexs82.exceptions.MapsException;

import java.util.*;
import java.util.stream.Collectors;

public enum Maps {
    RUSH_THEMEPARK("Seeds of Time", Modes.TURF_TAKEOVER, Modes.GARDENS_AND_GRAVEYARDS),
    RUSH_SNOW("Grete White North", Modes.TURF_TAKEOVER, Modes.GARDENS_AND_GRAVEYARDS),
    RUSH_SUBURBIA("Wall Nut Hills", Modes.TURF_TAKEOVER, Modes.GARDENS_AND_GRAVEYARDS),
    HERB_SPACE("Moon Base Z", Modes.TURF_TAKEOVER, Modes.HERBAL_ASSAULT),
    HERB_ZOMBURBIA("Zomburbia", Modes.TURF_TAKEOVER, Modes.HERBAL_ASSAULT),
    HERB_BRAINSTREET("Zombopolis", Modes.TURF_TAKEOVER, Modes.HERBAL_ASSAULT),
    FE_HUB("Backyard Battleground", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.CAPTURE_THE_TACO),
    COOP_ASIA("Zen Peak", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB, Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS),
    COOP_DINO("Boney Island", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB, Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS),
    COOP_EGYPT("Sandy Sands", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB, Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS),
    COOP_ROME("Colizeum", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB, Modes.SOIL_SURVIVORS),
    COOP_TIMEPARK("Time Park", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB),
    COOP_SPACE("Lunar Landing", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB, Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS),
    COOP_ZOMBOSSFACTORY("Z-Tech Factory", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB, Modes.SOIL_SURVIVORS),
    COOP_SNOW("Frosty Creek", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB, Modes.SOIL_SURVIVORS),
    COOP_MAINSTREET("Aqua Center", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB, Modes.SOIL_SURVIVORS),
    COOP_ZOMBURBIA("Frontline Flats", Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.SUBURBINATION, Modes.GNOME_BOMB, Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS),
    COOP_INFINITY_PLANE("Gnomiverse XL", Modes.CATS_VS_DINOS);

    private final String name;
    private final EnumSet<Modes> modes;
    private static final Random RANDOM = new Random();

    Maps(String name, Modes... modes) {
        this.name = name;
        this.modes = EnumSet.noneOf(Modes.class);
        Collections.addAll(this.modes, modes);
    }

    /**
     * Gets a random map that supports the specified modes, excluding any provided excluded maps.
     *
     * @param selectedModes the list of selected modes
     * @param excludedMaps the list of maps to exclude, may be null
     * @return a random map that supports the specified modes
     * @throws MapsException if all maps are excluded
     */
    @NotNull
    public static Maps getRandomMapForModes(@NotNull List<Modes> selectedModes, @Nullable List<Maps> excludedMaps) throws MapsException {
        if (selectedModes.contains(Modes.TURF_TAKEOVER)) {
            selectedModes.remove(Modes.GARDENS_AND_GRAVEYARDS);
            selectedModes.remove(Modes.HERBAL_ASSAULT);
        }

        List<Maps> matchingMaps = Arrays.stream(Maps.values())
                .filter(map -> map.getModes().stream().anyMatch(selectedModes::contains))
                .collect(Collectors.toList());

        if (excludedMaps != null && !excludedMaps.isEmpty()) {
            matchingMaps.removeAll(excludedMaps);
        }

        if (matchingMaps.isEmpty()) {
            throw new MapsException(MapsException.ErrorCode.ALL_MAPS_EXCLUDED);
        }

        for (int i = 0; i < 10; ++i) {
            Collections.shuffle(matchingMaps, RANDOM);
        }

        return matchingMaps.get(RANDOM.nextInt(matchingMaps.size()));
    }

    public String getName() {
        return name;
    }

    public EnumSet<Modes> getModes() {
        return modes;
    }
}
