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

        int mask = Utils.encodeEnums(maps);
        List<Maps> decodedMaps = Utils.decodeEnums(mask, Maps.class);

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

        int mask = Utils.encodeEnums(modes);
        List<Modes> decodedModes = Utils.decodeEnums(mask, Modes.class);

        Assertions.assertIterableEquals(modes, decodedModes);
    }
}
