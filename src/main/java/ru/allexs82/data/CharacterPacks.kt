package ru.allexs82.data

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

private const val embedColor = 0xffaec9

sealed class CharacterPacks(open val characters: List<ru.allexs82.data.Character>) {
    data class CharacterPack(override val characters: List<ru.allexs82.data.Character>) : CharacterPacks(characters)

    fun toEmbed(): MessageEmbed {
        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(embedColor)
        for (character: ru.allexs82.data.Character in characters) {
            embedBuilder.appendDescription(character.characterName + "\n")
        }
        return embedBuilder.build()
    }

    companion object {
        val FIRST = CharacterPack(
            listOf(
                ru.allexs82.data.Character.FIRE_FLOWER,
                ru.allexs82.data.Character.YETI_CHOMPER,
                ru.allexs82.data.Character.CAPTAIN_SQUAWK,
                ru.allexs82.data.Character.CAPTAIN_SHARKBITE
            )
        )
        val SECOND = CharacterPack(
            listOf(
                ru.allexs82.data.Character.TOXIC_PEA,
                ru.allexs82.data.Character.TOXIC_CITRON,
                ru.allexs82.data.Character.SHRIMP,
                ru.allexs82.data.Character.COZMIC_BRAINZ
            )
        )
        val THIRD = CharacterPack(
            listOf(
                ru.allexs82.data.Character.FIRE_PEA,
                ru.allexs82.data.Character.FIRE_CHOMPER,
                ru.allexs82.data.Character.LIL_DRAKE,
                ru.allexs82.data.Character.CAPTAIN_CANNON
            )
        )
        val FOURTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.CORN,
                ru.allexs82.data.Character.PETRIFIED_CACTUS,
                ru.allexs82.data.Character.SUPER_COMMANDO,
                ru.allexs82.data.Character.BREAKFAST_BRAINZ
            )
        )
        val FIFTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.ALIEN_FLOWER,
                ru.allexs82.data.Character.CITRON,
                ru.allexs82.data.Character.CAPTAIN_FLAMEFACE,
                ru.allexs82.data.Character.SUPER_COMMANDO
            )
        )
        val SIXTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.PEASHOOTER,
                ru.allexs82.data.Character.COMMANDO_PEA,
                ru.allexs82.data.Character.MARINE_BIOLOGIST,
                ru.allexs82.data.Character.ARCHEOLOGIST
            )
        )
        val SEVENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.CAMO_CACTUS,
                ru.allexs82.data.Character.FROST_ROSE,
                ru.allexs82.data.Character.CAPTAIN_PARTYMAN,
                ru.allexs82.data.Character.ELECTRO_BRAINZ
            )
        )
        val EIGHT = CharacterPack(
            listOf(
                ru.allexs82.data.Character.AGENT_PEA,
                ru.allexs82.data.Character.CHOMP_THING,
                ru.allexs82.data.Character.ASTRONAUT,
                ru.allexs82.data.Character.ARCHEOLOGIST
            )
        )
        val NINTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.POWER_FLOWER,
                ru.allexs82.data.Character.ELECTRO_PEA,
                ru.allexs82.data.Character.SCUBA_SOLDIER,
                ru.allexs82.data.Character.ROADIE_Z
            )
        )
        val TENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.DRUID_ROSE,
                ru.allexs82.data.Character.TOXIC_CHOMPER,
                ru.allexs82.data.Character.IMP,
                ru.allexs82.data.Character.PLUMBER
            )
        )
        val ELEVENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.BBQ_CORN,
                ru.allexs82.data.Character.FROZEN_CITRON,
                ru.allexs82.data.Character.HOCKEY_STAR,
                ru.allexs82.data.Character.PYLON_IMP
            )
        )
        val TWELFTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.FUTURE_CACTUS,
                ru.allexs82.data.Character.FIRE_ROSE,
                ru.allexs82.data.Character.DR_TOXIC,
                ru.allexs82.data.Character.WRESTLING_STAR
            )
        )
        val THIRTEENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.CACTUS,
                ru.allexs82.data.Character.ROCK_PEA,
                ru.allexs82.data.Character.ALL_STAR,
                ru.allexs82.data.Character.SKY_TROOPER
            )
        )
        val FOURTEENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.PARTY_CORN,
                ru.allexs82.data.Character.PARTY_ROSE,
                ru.allexs82.data.Character.COMPUTER_SCIENTIST,
                ru.allexs82.data.Character.PARTY_BRAINZ
            )
        )
        val FIFTEENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.ZEN_CACTUS,
                ru.allexs82.data.Character.PARTY_CITRON,
                ru.allexs82.data.Character.PARK_RANGER,
                ru.allexs82.data.Character.AC_PERRY
            )
        )
        val SIXTEENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.PLASMA_PEA,
                ru.allexs82.data.Character.ELECTRO_CITRON,
                ru.allexs82.data.Character.SOLDIER,
                ru.allexs82.data.Character.GOLF_STAR
            )
        )
        val SEVENTEENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.COMMANDO_CORN,
                ru.allexs82.data.Character.CHOMPER,
                ru.allexs82.data.Character.ARCTIC_TROOPER,
                ru.allexs82.data.Character.PALEONTOLOGIST
            )
        )
        val EIGHTEENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.LAW_PEA,
                ru.allexs82.data.Character.JADE_CACTUS,
                ru.allexs82.data.Character.GOALIE_STAR,
                ru.allexs82.data.Character.MECHANIC
            )
        )
        val NINETEENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.VAMPIRE_FLOWER,
                ru.allexs82.data.Character.IRON_CITRON,
                ru.allexs82.data.Character.PAINTER,
                ru.allexs82.data.Character.ZOOLOGIST
            )
        )
        val TWENTIETH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.STUFFY_FLOWER,
                ru.allexs82.data.Character.ROSE,
                ru.allexs82.data.Character.LANDSCAPER,
                ru.allexs82.data.Character.RUGBY_STAR
            )
        )
        val TWENTY_FIRST = CharacterPack(
            listOf(
                ru.allexs82.data.Character.SUNFLOWER,
                ru.allexs82.data.Character.POPS_CORN,
                ru.allexs82.data.Character.CHEMIST,
                ru.allexs82.data.Character.TANK_COMMANDER
            )
        )
        val TWENTY_SECOND = CharacterPack(
            listOf(
                ru.allexs82.data.Character.POWER_CACTUS,
                ru.allexs82.data.Character.POWER_CHOMPER,
                ru.allexs82.data.Character.WELDER,
                ru.allexs82.data.Character.HOVER_GOAT
            )
        )
        val TWENTY_THIRD = CharacterPack(
            listOf(
                ru.allexs82.data.Character.MOB_COB,
                ru.allexs82.data.Character.BANDIT_CACTUS,
                ru.allexs82.data.Character.CAMO_RANGER,
                ru.allexs82.data.Character.ELECTRICIAN
            )
        )
        val TWENTY_FOURTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.MOB_COB,
                ru.allexs82.data.Character.BANDIT_CACTUS,
                ru.allexs82.data.Character.CAMO_RANGER,
                ru.allexs82.data.Character.ELECTRICIAN
            )
        )
        val TWENTY_FIFTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.FIRE_CACTUS,
                ru.allexs82.data.Character.NEC_ROSE,
                ru.allexs82.data.Character.SUPER_BRAINZ,
                ru.allexs82.data.Character.TENNIS_STAR
            )
        )
        val TWENTY_SIXTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.COUNT_CHOMPULA,
                ru.allexs82.data.Character.ICE_PEA,
                ru.allexs82.data.Character.CENTURION,
                ru.allexs82.data.Character.SCIENTIST
            )
        )
        val TWENTY_SEVENTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.DISCO_CHOMPER,
                ru.allexs82.data.Character.ICE_CACTUS,
                ru.allexs82.data.Character.CAPTAIN,
                ru.allexs82.data.Character.PARTY_IMP
            )
        )
        val TWENTY_EIGHTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.TORCHWOOD,
                ru.allexs82.data.Character.ARMOR_CHOMPER,
                ru.allexs82.data.Character.Z7_IMP,
                ru.allexs82.data.Character.MOTO_X_STAR
            )
        )
        val TWENTY_NINTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.SHADOW_FLOWER,
                ru.allexs82.data.Character.HOT_ROD_CHOMPER,
                ru.allexs82.data.Character.TOXIC_BRAINZ,
                ru.allexs82.data.Character.ENGINEER
            )
        )
        val THIRTIETH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.METAL_PETAL,
                ru.allexs82.data.Character.SUN_PHARAOH,
                ru.allexs82.data.Character.CRICKET_STAR,
                ru.allexs82.data.Character.PHYSICIST
            )
        )
        val THIRTY_FIRST = CharacterPack(
            listOf(
                ru.allexs82.data.Character.MYSTIC_FLOWER,
                ru.allexs82.data.Character.SCALLYWAG_IMP,
                ru.allexs82.data.Character.SANITATION_EXPERT
            )
        )

        val THIRTY_SECOND = CharacterPack(
            listOf(
                ru.allexs82.data.Character.CAMO_CACTUS,
                ru.allexs82.data.Character.PETRIFIED_CACTUS,
                ru.allexs82.data.Character.ELECTRO_PEA,
                ru.allexs82.data.Character.CAMO_RANGER,
                ru.allexs82.data.Character.CAPTAIN_CANNON,
                ru.allexs82.data.Character.CAPTAIN_SQUAWK
            )
        )
        val THIRTY_THIRD = CharacterPack(
            listOf(
                ru.allexs82.data.Character.AGENT_PEA,
                ru.allexs82.data.Character.TOXIC_CITRON,
                ru.allexs82.data.Character.ARMOR_CHOMPER,
                ru.allexs82.data.Character.FROST_ROSE,
                ru.allexs82.data.Character.TOXIC_BRAINZ,
                ru.allexs82.data.Character.PARTY_BRAINZ,
                ru.allexs82.data.Character.ELECTRO_BRAINZ,
                ru.allexs82.data.Character.COZMIC_BRAINZ
            )
        )
        val THIRTY_FOURTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.TORCHWOOD,
                ru.allexs82.data.Character.YETI_CHOMPER,
                ru.allexs82.data.Character.FIRE_ROSE,
                ru.allexs82.data.Character.CHOMP_THING,
                ru.allexs82.data.Character.ALL_STAR,
                ru.allexs82.data.Character.ZOOLOGIST,
                ru.allexs82.data.Character.ROADIE_Z,
                ru.allexs82.data.Character.BREAKFAST_BRAINZ
            )
        )
        val THIRTY_FIFTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.SOLDIER,
                ru.allexs82.data.Character.PAINTER,
                ru.allexs82.data.Character.ARCHEOLOGIST,
                ru.allexs82.data.Character.ROADIE_Z,
                ru.allexs82.data.Character.FUTURE_CACTUS,
                ru.allexs82.data.Character.PLASMA_PEA,
                ru.allexs82.data.Character.CHOMP_THING,
                ru.allexs82.data.Character.ELECTRO_CITRON
            )
        )
        val THIRTY_SIXTH = CharacterPack(
            listOf(
                ru.allexs82.data.Character.AGENT_PEA,
                ru.allexs82.data.Character.TORCHWOOD,
                ru.allexs82.data.Character.POWER_CHOMPER,
                ru.allexs82.data.Character.TOXIC_CHOMPER,
                ru.allexs82.data.Character.FIRE_CHOMPER,
                ru.allexs82.data.Character.HOCKEY_STAR,
                ru.allexs82.data.Character.SUPER_COMMANDO,
                ru.allexs82.data.Character.COZMIC_BRAINZ,
                ru.allexs82.data.Character.ELECTRO_BRAINZ,
                ru.allexs82.data.Character.BREAKFAST_BRAINZ
            )
        )

        val allPacks = listOf(
            FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH, EIGHT,
            NINTH, TENTH, ELEVENTH, TWELFTH, THIRTEENTH, FOURTEENTH,
            FIFTEENTH, SIXTEENTH, SEVENTEENTH, EIGHTEENTH, NINETEENTH,
            TWENTIETH, TWENTY_FIRST, TWENTY_SECOND, TWENTY_THIRD,
            TWENTY_FOURTH, TWENTY_FIFTH, TWENTY_SIXTH, TWENTY_SEVENTH,
            TWENTY_EIGHTH, TWENTY_NINTH, THIRTIETH, THIRTY_FIRST,
            THIRTY_SECOND, THIRTY_THIRD, THIRTY_FOURTH, THIRTY_FIFTH,
            THIRTY_SIXTH
        )
    }
}
