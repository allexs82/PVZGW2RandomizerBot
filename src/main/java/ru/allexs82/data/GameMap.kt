package ru.allexs82.data

import ru.allexs82.data.enums.Modes
import kotlin.random.Random

sealed class GameMap(open val mapName: String, open val modes: List<Modes>) {

    data class CoopGameMap(
        override val mapName: String,
        val additionalModes: List<Modes>
    ) : GameMap(mapName, listOf(Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.GNOME_BOMB, Modes.SUBURBINATION) + additionalModes)

    data class TTFGameMap(
        override val mapName: String,
        val mode: Modes
    ) : GameMap(mapName, listOf(Modes.TURF_TAKEOVER, mode))

    data class PureGameMap(
        override val mapName: String,
        override val modes: List<Modes>
    ) : GameMap(mapName, modes)

    companion object {
        val RUSH_THEMEPARK = TTFGameMap("Seeds of Time", Modes.GARDENS_AND_GRAVEYARDS)
        val RUSH_SNOW = TTFGameMap("Great White North", Modes.GARDENS_AND_GRAVEYARDS)
        val RUSH_SUBURBIA = TTFGameMap("Wall-Nut Hills", Modes.GARDENS_AND_GRAVEYARDS)
        val HERB_SPACE = TTFGameMap("Moon Base Z", Modes.HERBAL_ASSAULT)
        val HERB_ZOMBURBIA = TTFGameMap("Zomburbia", Modes.HERBAL_ASSAULT)
        val HERB_BRAINSTREET = TTFGameMap("Zombopolis", Modes.HERBAL_ASSAULT)

        val FE_HUB = PureGameMap("Backyard Battleground", listOf(Modes.TEAM_VANQUISH, Modes.VANQUISH_CONFIRMED, Modes.CAPTURE_THE_TACO))

        val COOP_ASIA = CoopGameMap("Zen Peak", listOf(Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS))
        val COOP_DINO = CoopGameMap("Boney Island", listOf(Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS))
        val COOP_EGYPT = CoopGameMap("Sandy Sands", listOf(Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS))
        val COOP_ROME = CoopGameMap("Colizeum", listOf(Modes.SOIL_SURVIVORS))
        val COOP_TIMEPARK = CoopGameMap("Time Park", listOf())
        val COOP_SPACE = CoopGameMap("Lunar Landing", listOf(Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS))
        val COOP_ZOMBOSSFACTORY = CoopGameMap("Z-Tech Factory", listOf(Modes.SOIL_SURVIVORS))
        val COOP_SNOW = CoopGameMap("Frosty Creek", listOf(Modes.SOIL_SURVIVORS))
        val COOP_ZOMBURBIA = CoopGameMap("Frontline Flats", listOf(Modes.CAPTURE_THE_TACO, Modes.SOIL_SURVIVORS))

        val COOP_INFINITY_PLANE = PureGameMap("Gnomiverse XL", listOf(Modes.CATS_VS_DINOS))

        val allMaps = listOf(
            RUSH_THEMEPARK,
            RUSH_SNOW,
            RUSH_SUBURBIA,
            HERB_SPACE,
            HERB_ZOMBURBIA,
            HERB_BRAINSTREET,
            FE_HUB,
            COOP_ASIA,
            COOP_DINO,
            COOP_EGYPT,
            COOP_ROME,
            COOP_TIMEPARK,
            COOP_SPACE,
            COOP_ZOMBOSSFACTORY,
            COOP_SNOW,
            COOP_ZOMBURBIA,
            COOP_INFINITY_PLANE
        )

        /**
         * Returns a random [GameMap] that supports the specified [selectedModes],
         * excluding any [mapsToExclude].
         *
         * @param selectedModes list of [Modes] to filter for
         * @param mapsToExclude list of [GameMap] to exclude, may be null
         * @return a random [GameMap] that supports the specified [selectedModes], or null if all maps were excluded due to them being in [mapsToExclude] list.
         * @throws RuntimeException if [selectedModes] is empty
         */
        fun getRandomMapForModes(selectedModes: List<Modes>, mapsToExclude: List<GameMap>?): GameMap? {
            if (selectedModes.isEmpty()) {
                throw RuntimeException("selectedModes can't be null")
            }

            val filteredModes = selectedModes.toMutableSet()

            if (Modes.TURF_TAKEOVER in filteredModes) {
                filteredModes.remove(Modes.GARDENS_AND_GRAVEYARDS)
                filteredModes.remove(Modes.HERBAL_ASSAULT)
            }

            val matchingMaps = allMaps.filter { map ->
                map.modes.any { it in filteredModes }
            }.toMutableList()

            mapsToExclude?.let { matchingMaps.removeAll(it) }

            if (matchingMaps.isEmpty()) {
                return null
            }

            repeat(10) {
                matchingMaps.shuffle()
            }

            return matchingMaps[Random.nextInt(matchingMaps.size)]
        }
    }
}
