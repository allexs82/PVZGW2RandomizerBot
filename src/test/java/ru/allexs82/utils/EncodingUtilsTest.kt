package ru.allexs82.utils

import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import ru.allexs82.data.GameMap
import ru.allexs82.data.enums.Modes
import kotlin.random.Random

class EncodingUtilsTest {

    @Test
    fun encodeAndDecodeExcludedMaps_AllGood() {
        val mapsList = mutableListOf<GameMap>()
        for (map in GameMap.allMaps) {
            if (Random.nextBoolean()) {
                mapsList.add(map)
            }
        }

        val mask = EncodingUtils.encodeList(mapsList, GameMap.allMaps)
        val decodedMaps = EncodingUtils.decodeList(mask, GameMap.allMaps)

        assertIterableEquals(mapsList, decodedMaps)
    }

    @Test
    fun encodeAndDecodeSelectedModes_AllGood() {
        val modesList = mutableListOf<Modes>()
        for (mode in Modes.entries) {
            if (Random.nextBoolean()) {
                modesList.add(mode)
            }
        }

        val mask = EncodingUtils.encodeList(modesList, Modes.entries)
        val decodedModes = EncodingUtils.decodeList(mask, Modes.entries)

        assertIterableEquals(modesList, decodedModes)
    }
}