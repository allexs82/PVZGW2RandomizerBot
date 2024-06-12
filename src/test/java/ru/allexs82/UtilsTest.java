package ru.allexs82;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.allexs82.enums.Maps;
import ru.allexs82.enums.Modes;

import java.util.*;

public class UtilsTest {
    private static final Random random = new Random();

    @Test
    public void encodeAndDecodeExcludedMaps_AllGood() {
        List<Maps> maps = new ArrayList<>();
        for (Maps map : Maps.values()) {
            if (random.nextBoolean()) {
                maps.add(map);
            }
        }

        int mask = Utils.encodeExcludedMaps(maps);
        List<Maps> decodedMaps = Utils.decodeExcludedMaps(mask);

        Assertions.assertIterableEquals(maps, decodedMaps);
    }

    @Test
    public void encodeAndDecodeSelectedModes_AllGood() {
        List<Modes> modes = new ArrayList<>();
        for (Modes mode : Modes.values()) {
            if (random.nextBoolean()) {
                modes.add(mode);
            }
        }

        int mask = Utils.encodeSelectedModes(modes);
        List<Modes> decodedModes = Utils.decodeSelectedModes(mask);

        Assertions.assertIterableEquals(modes, decodedModes);
    }
}
