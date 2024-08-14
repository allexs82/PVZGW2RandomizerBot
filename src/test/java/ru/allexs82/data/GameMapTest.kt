package ru.allexs82.data

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.allexs82.data.enums.Modes

class GameMapTest {

    @Test
    fun getRandomMapForModes_AllGood() {
        val modes = listOf(Modes.HERBAL_ASSAULT)
        val excludedMaps = listOf(GameMap.FE_HUB, GameMap.COOP_ASIA, GameMap.HERB_SPACE, GameMap.HERB_ZOMBURBIA)

        assertSame(GameMap.getRandomMapForModes(modes, excludedMaps), GameMap.HERB_BRAINSTREET)
    }

    @Test
    fun getRandomMapForModes_AllExcluded() {
        val modes = listOf(Modes.HERBAL_ASSAULT)
        val excludedMaps = listOf(GameMap.FE_HUB, GameMap.COOP_ASIA, GameMap.HERB_SPACE, GameMap.HERB_ZOMBURBIA, GameMap.HERB_BRAINSTREET)

        assertNull(GameMap.getRandomMapForModes(modes, excludedMaps))
    }
}