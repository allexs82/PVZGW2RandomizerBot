package ru.allexs82.deprecated.enums;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.allexs82.exceptions.MapsException;

import java.util.*;
import java.util.stream.Collectors;

@Deprecated
public enum Maps {
    RUSH_THEMEPARK("Seeds of Time", ModesOld.TURF_TAKEOVER, ModesOld.GARDENS_AND_GRAVEYARDS),
    RUSH_SNOW("Great White North", ModesOld.TURF_TAKEOVER, ModesOld.GARDENS_AND_GRAVEYARDS),
    RUSH_SUBURBIA("Wall Nut Hills", ModesOld.TURF_TAKEOVER, ModesOld.GARDENS_AND_GRAVEYARDS),
    HERB_SPACE("Moon Base Z", ModesOld.TURF_TAKEOVER, ModesOld.HERBAL_ASSAULT),
    HERB_ZOMBURBIA("Zomburbia", ModesOld.TURF_TAKEOVER, ModesOld.HERBAL_ASSAULT),
    HERB_BRAINSTREET("Zombopolis", ModesOld.TURF_TAKEOVER, ModesOld.HERBAL_ASSAULT),
    FE_HUB("Backyard Battleground", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.CAPTURE_THE_TACO),
    COOP_ASIA("Zen Peak", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB, ModesOld.CAPTURE_THE_TACO, ModesOld.SOIL_SURVIVORS),
    COOP_DINO("Boney Island", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB, ModesOld.CAPTURE_THE_TACO, ModesOld.SOIL_SURVIVORS),
    COOP_EGYPT("Sandy Sands", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB, ModesOld.CAPTURE_THE_TACO, ModesOld.SOIL_SURVIVORS),
    COOP_ROME("Colizeum", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB, ModesOld.SOIL_SURVIVORS),
    COOP_TIMEPARK("Time Park", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB),
    COOP_SPACE("Lunar Landing", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB, ModesOld.CAPTURE_THE_TACO, ModesOld.SOIL_SURVIVORS),
    COOP_ZOMBOSSFACTORY("Z-Tech Factory", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB, ModesOld.SOIL_SURVIVORS),
    COOP_SNOW("Frosty Creek", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB, ModesOld.SOIL_SURVIVORS),
    COOP_MAINSTREET("Aqua Center", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB, ModesOld.SOIL_SURVIVORS),
    COOP_ZOMBURBIA("Frontline Flats", ModesOld.TEAM_VANQUISH, ModesOld.VANQUISH_CONFIRMED, ModesOld.SUBURBINATION, ModesOld.GNOME_BOMB, ModesOld.CAPTURE_THE_TACO, ModesOld.SOIL_SURVIVORS),
    COOP_INFINITY_PLANE("Gnomiverse XL", ModesOld.CATS_VS_DINOS);

    private final String name;
    private final EnumSet<ModesOld> modes;
    private static final Random RANDOM = new Random();

    Maps(String name, ModesOld... modes) {
        this.name = name;
        this.modes = EnumSet.noneOf(ModesOld.class);
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
    public static Maps getRandomMapForModes(@NotNull List<ModesOld> selectedModes, @Nullable List<Maps> excludedMaps) throws MapsException {
        if (selectedModes.contains(ModesOld.TURF_TAKEOVER)) {
            selectedModes.remove(ModesOld.GARDENS_AND_GRAVEYARDS);
            selectedModes.remove(ModesOld.HERBAL_ASSAULT);
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

    public EnumSet<ModesOld> getModes() {
        return modes;
    }
}
