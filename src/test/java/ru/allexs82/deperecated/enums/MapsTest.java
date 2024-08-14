package ru.allexs82.deperecated.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.allexs82.exceptions.MapsException;

import java.util.*;

@Deprecated
class MapsTest {

    @Test
    void getRandomMapForModes_AllGood() throws MapsException {
        List<ModesOld> modes = Collections.singletonList(ModesOld.HERBAL_ASSAULT);
        List<Maps> excludedMaps = Arrays.asList(Maps.FE_HUB, Maps.COOP_ASIA, Maps.HERB_SPACE, Maps.HERB_ZOMBURBIA);

        Assertions.assertSame(Maps.getRandomMapForModes(modes, excludedMaps), Maps.HERB_BRAINSTREET);
    }

    @Test
    void getRandomMapForModes_AllExcluded() {
        List<ModesOld> modes = Collections.singletonList(ModesOld.HERBAL_ASSAULT);
        List<Maps> excludedMaps = Arrays.asList(Maps.FE_HUB, Maps.COOP_ASIA, Maps.HERB_SPACE, Maps.HERB_ZOMBURBIA, Maps.HERB_BRAINSTREET);

        Executable executable = () -> Maps.getRandomMapForModes(modes, excludedMaps);

        Assertions.assertThrows(MapsException.class, executable);
    }
}