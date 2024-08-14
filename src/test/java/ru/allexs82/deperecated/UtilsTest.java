package ru.allexs82.deperecated;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.allexs82.deperecated.enums.Maps;
import ru.allexs82.deperecated.enums.ModesOld;

import java.util.*;

@Deprecated
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
        List<ModesOld> modes = new ArrayList<>();
        for (ModesOld mode : ModesOld.values()) {
            if (random.nextBoolean()) {
                modes.add(mode);
            }
        }

        int mask = Utils.encodeEnums(modes);
        List<ModesOld> decodedModes = Utils.decodeEnums(mask, ModesOld.class);

        Assertions.assertIterableEquals(modes, decodedModes);
    }
}
