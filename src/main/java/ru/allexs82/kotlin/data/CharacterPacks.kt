package ru.allexs82.kotlin.data

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

private const val embedColor = 0xffaec9

sealed class CharacterPacks(open val characters: List<Character>) {
    data class CharacterPack(override val characters: List<Character>) : CharacterPacks(characters)

    fun toEmbed(): MessageEmbed {
        val embedBuilder = EmbedBuilder()
        embedBuilder.setColor(embedColor)
        for (character: Character in characters) {
            embedBuilder.appendDescription(character.name + "\n")
        }
        return embedBuilder.build()
    }

    companion object {
        val FIRST = CharacterPack(
            listOf(
                Character.FIRE_FLOWER,
                Character.YETI_CHOMPER,
                Character.CAPTAIN_SQUAWK,
                Character.CAPTAIN_SHARKBITE
            )
        )
        val SECOND = CharacterPack(
            listOf(
                Character.TOXIC_PEA,
                Character.TOXIC_CITRON,
                Character.SHRIMP,
                Character.COZMIC_BRAINZ
            )
        )
        val THIRD = CharacterPack(
            listOf(
                Character.FIRE_PEA,
                Character.FIRE_CHOMPER,
                Character.LIL_DRAKE,
                Character.CAPTAIN_CANNON
            )
        )
        val FOURTH = CharacterPack(
            listOf(
                Character.CORN,
                Character.PETRIFIED_CACTUS,
                Character.SUPER_COMMANDO,
                Character.BREAKFAST_BRAINZ
            )
        )
        val FIFTH = CharacterPack(
            listOf(
                Character.ALIEN_FLOWER,
                Character.CITRON,
                Character.CAPTAIN_FLAMEFACE,
                Character.SUPER_COMMANDO
            )
        )
        val SIXTH = CharacterPack(
            listOf(
                Character.PEASHOOTER,
                Character.COMMANDO_PEA,
                Character.MARINE_BIOLOGIST,
                Character.ARCHEOLOGIST
            )
        )
        val SEVENTH = CharacterPack(
            listOf(
                Character.CAMO_CACTUS,
                Character.FROST_ROSE,
                Character.CAPTAIN_PARTYMAN,
                Character.ELECTRO_BRAINZ
            )
        )
        val EIGHT = CharacterPack(
            listOf(
                Character.AGENT_PEA,
                Character.CHOMP_THING,
                Character.ASTRONAUT,
                Character.ARCHEOLOGIST
            )
        )
        val NINTH = CharacterPack(
            listOf(
                Character.POWER_FLOWER,
                Character.ELECTRO_PEA,
                Character.SCUBA_SOLDIER,
                Character.ROADIE_Z
            )
        )
        val TENTH = CharacterPack(
            listOf(
                Character.DRUID_ROSE,
                Character.TOXIC_CHOMPER,
                Character.IMP,
                Character.PLUMBER
            )
        )
        val ELEVENTH = CharacterPack(
            listOf(
                Character.BBQ_CORN,
                Character.FROZEN_CITRON,
                Character.HOCKEY_STAR,
                Character.PYLON_IMP
            )
        )
        val TWELFTH = CharacterPack(
            listOf(
                Character.FUTURE_CACTUS,
                Character.FIRE_ROSE,
                Character.DR_TOXIC,
                Character.WRESTLING_STAR
            )
        )
        val THIRTEENTH = CharacterPack(
            listOf(
                Character.CACTUS,
                Character.ROCK_PEA,
                Character.ALL_STAR,
                Character.SKY_TROOPER
            )
        )
        val FOURTEENTH = CharacterPack(
            listOf(
                Character.PARTY_CORN,
                Character.PARTY_ROSE,
                Character.COMPUTER_SCIENTIST,
                Character.PARTY_BRAINZ
            )
        )
        val FIFTEENTH = CharacterPack(
            listOf(
                Character.ZEN_CACTUS,
                Character.PARTY_CITRON,
                Character.PARK_RANGER,
                Character.AC_PERRY
            )
        )
        val SIXTEENTH = CharacterPack(
            listOf(
                Character.PLASMA_PEA,
                Character.ELECTRO_CITRON,
                Character.SOLDIER,
                Character.GOLF_STAR
            )
        )
        val SEVENTEENTH = CharacterPack(
            listOf(
                Character.COMMANDO_CORN,
                Character.CHOMPER,
                Character.ARCTIC_TROOPER,
                Character.PALEONTOLOGIST
            )
        )
        val EIGHTEENTH = CharacterPack(
            listOf(
                Character.LAW_PEA,
                Character.JADE_CACTUS,
                Character.GOALIE_STAR,
                Character.MECHANIC
            )
        )
        val NINETEENTH = CharacterPack(
            listOf(
                Character.VAMPIRE_FLOWER,
                Character.IRON_CITRON,
                Character.PAINTER,
                Character.ZOOLOGIST
            )
        )
        val TWENTIETH = CharacterPack(
            listOf(
                Character.STUFFY_FLOWER,
                Character.ROSE,
                Character.LANDSCAPER,
                Character.RUGBY_STAR
            )
        )
        val TWENTY_FIRST = CharacterPack(
            listOf(
                Character.SUNFLOWER,
                Character.POPS_CORN,
                Character.CHEMIST,
                Character.TANK_COMMANDER
            )
        )
        val TWENTY_SECOND = CharacterPack(
            listOf(
                Character.POWER_CACTUS,
                Character.POWER_CHOMPER,
                Character.WELDER,
                Character.HOVER_GOAT
            )
        )
        val TWENTY_THIRD = CharacterPack(
            listOf(
                Character.MOB_COB,
                Character.BANDIT_CACTUS,
                Character.CAMO_RANGER,
                Character.ELECTRICIAN
            )
        )
        val TWENTY_FOURTH = CharacterPack(
            listOf(
                Character.MOB_COB,
                Character.BANDIT_CACTUS,
                Character.CAMO_RANGER,
                Character.ELECTRICIAN
            )
        )
        val TWENTY_FIFTH = CharacterPack(
            listOf(
                Character.FIRE_CACTUS,
                Character.NEC_ROSE,
                Character.SUPER_BRAINZ,
                Character.TENNIS_STAR
            )
        )
        val TWENTY_SIXTH = CharacterPack(
            listOf(
                Character.COUNT_CHOMPULA,
                Character.ICE_PEA,
                Character.CENTURION,
                Character.SCIENTIST
            )
        )
        val TWENTY_SEVENTH = CharacterPack(
            listOf(
                Character.DISCO_CHOMPER,
                Character.ICE_CACTUS,
                Character.CAPTAIN,
                Character.PARTY_IMP
            )
        )
        val TWENTY_EIGHTH = CharacterPack(
            listOf(
                Character.TORCHWOOD,
                Character.ARMOR_CHOMPER,
                Character.Z7_IMP,
                Character.MOTO_X_STAR
            )
        )
        val TWENTY_NINTH = CharacterPack(
            listOf(
                Character.SHADOW_FLOWER,
                Character.HOT_ROD_CHOMPER,
                Character.TOXIC_BRAINZ,
                Character.ENGINEER
            )
        )
        val THIRTIETH = CharacterPack(
            listOf(
                Character.METAL_PETAL,
                Character.SUN_PHARAOH,
                Character.CRICKET_STAR,
                Character.PHYSICIST
            )
        )
        val THIRTY_FIRST = CharacterPack(
            listOf(
                Character.MYSTIC_FLOWER,
                Character.SCALLYWAG_IMP,
                Character.SANITATION_EXPERT
            )
        )

        val THIRTY_SECOND = CharacterPack(
            listOf(
                Character.CAMO_CACTUS,
                Character.PETRIFIED_CACTUS,
                Character.ELECTRO_PEA,
                Character.CAMO_RANGER,
                Character.CAPTAIN_CANNON,
                Character.CAPTAIN_SQUAWK
            )
        )
        val THIRTY_THIRD = CharacterPack(
            listOf(
                Character.AGENT_PEA,
                Character.TOXIC_CITRON,
                Character.ARMOR_CHOMPER,
                Character.FROST_ROSE,
                Character.TOXIC_BRAINZ,
                Character.PARTY_BRAINZ,
                Character.ELECTRO_BRAINZ,
                Character.COZMIC_BRAINZ
            )
        )
        val THIRTY_FOURTH = CharacterPack(
            listOf(
                Character.TORCHWOOD,
                Character.YETI_CHOMPER,
                Character.FIRE_ROSE,
                Character.CHOMP_THING,
                Character.ALL_STAR,
                Character.ZOOLOGIST,
                Character.ROADIE_Z,
                Character.BREAKFAST_BRAINZ
            )
        )
        val THIRTY_FIFTH = CharacterPack(
            listOf(
                Character.SOLDIER,
                Character.PAINTER,
                Character.ARCHEOLOGIST,
                Character.ROADIE_Z,
                Character.FUTURE_CACTUS,
                Character.PLASMA_PEA,
                Character.CHOMP_THING,
                Character.ELECTRO_CITRON
            )
        )
        val THIRTY_SIXTH = CharacterPack(
            listOf(
                Character.AGENT_PEA,
                Character.TORCHWOOD,
                Character.POWER_CHOMPER,
                Character.TOXIC_CHOMPER,
                Character.FIRE_CHOMPER,
                Character.HOCKEY_STAR,
                Character.SUPER_COMMANDO,
                Character.COZMIC_BRAINZ,
                Character.ELECTRO_BRAINZ,
                Character.BREAKFAST_BRAINZ
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
