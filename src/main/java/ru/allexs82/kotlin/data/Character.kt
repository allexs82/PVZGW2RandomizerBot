package ru.allexs82.kotlin.data

import ru.allexs82.kotlin.data.enums.Classes
import ru.allexs82.kotlin.data.enums.Sides
import kotlin.random.Random

sealed class Character(open val name: String, val charClass: Classes, val side: Sides) {
    // Plant characters
    data class Citron(override val name: String) : Character(name, Classes.CITRON, Sides.PLANTS)
    data class Rose(override val name: String) : Character(name, Classes.ROSE, Sides.PLANTS)
    data class Corn(override val name: String) : Character(name, Classes.CORN, Sides.PLANTS)
    data class Peashooter(override val name: String) : Character(name, Classes.PEASHOOTER, Sides.PLANTS)
    data class Chomper(override val name: String) : Character(name, Classes.CHOMPER, Sides.PLANTS)
    data class Sunflower(override val name: String) : Character(name, Classes.SUNFLOWER, Sides.PLANTS)
    data class Cactus(override val name: String) : Character(name, Classes.CACTUS, Sides.PLANTS)
    data class Torchwood(override val name: String) : Character(name, Classes.TORCHWOOD, Sides.PLANTS)

    // Zombie characters
    data class Imp(override val name: String) : Character(name, Classes.IMP, Sides.ZOMBIES)
    data class SuperBrainz(override val name: String) : Character(name, Classes.SUPER_BRAINZ, Sides.ZOMBIES)
    data class Captain(override val name: String) : Character(name, Classes.CAPTAIN, Sides.ZOMBIES)
    data class Soldier(override val name: String) : Character(name, Classes.SOLDIER, Sides.ZOMBIES)
    data class Engineer(override val name: String) : Character(name, Classes.ENGINEER, Sides.ZOMBIES)
    data class Scientist(override val name: String) : Character(name, Classes.SCIENTIST, Sides.ZOMBIES)
    data class AllStar(override val name: String) : Character(name, Classes.ALL_STAR, Sides.ZOMBIES)
    data class HoverGoat(override val name: String) : Character(name, Classes.HOVER_GOAT, Sides.ZOMBIES)

    companion object {
        fun getRandomCharacterForSide(side: Sides): Character {
            val matchingCharacters = when (side) {
                Sides.PLANTS -> allPlants
                Sides.ZOMBIES -> allZombies
                Sides.ANY -> allCharacters
            }.toMutableList()

            repeat(10) {
                matchingCharacters.shuffle()
            }
            return matchingCharacters[Random.nextInt(matchingCharacters.size)]
        }
        // Citron characters
        val CITRON = Citron("Citron")
        val IRON_CITRON = Citron("Iron Citron")
        val ELECTRO_CITRON = Citron("Electro Citron")
        val FROZEN_CITRON = Citron("Frozen Citron")
        val PARTY_CITRON = Citron("Party Citron")
        val TOXIC_CITRON = Citron("Toxic Citron")

        // Rose characters
        val ROSE = Rose("Rose")
        val DRUID_ROSE = Rose("Druid Rose")
        val FIRE_ROSE = Rose("Fire Rose")
        val FROST_ROSE = Rose("Frost Rose")
        val PARTY_ROSE = Rose("Party Rose")
        val NEC_ROSE = Rose("Nec'Rose")

        // Corn characters
        val CORN = Corn("Kernel Corn")
        val BBQ_CORN = Corn("BBQ Corn")
        val MOB_COB = Corn("Mob Cob")
        val POPS_CORN = Corn("Pops Corn")
        val PARTY_CORN = Corn("Party Corn")
        val COMMANDO_CORN = Corn("Commando Corn")

        // Peashooter characters
        val PEASHOOTER = Peashooter("Peashooter")
        val FIRE_PEA = Peashooter("Fire Pea")
        val ICE_PEA = Peashooter("Ice Pea")
        val TOXIC_PEA = Peashooter("Toxic Pea")
        val COMMANDO_PEA = Peashooter("Commando Pea")
        val AGENT_PEA = Peashooter("Agent Pea")
        val LAW_PEA = Peashooter("Law Pea")
        val PLASMA_PEA = Peashooter("Plasma Pea")
        val ROCK_PEA = Peashooter("Rock Pea")
        val ELECTRO_PEA = Peashooter("Electro Pea")

        // Chomper characters
        val CHOMPER = Chomper("Chomper")
        val FIRE_CHOMPER = Chomper("Fire Chomper")
        val HOT_ROD_CHOMPER = Chomper("Hot Rod Chomper")
        val POWER_CHOMPER = Chomper("Power Chomper")
        val TOXIC_CHOMPER = Chomper("Toxic Chomper")
        val COUNT_CHOMPULA = Chomper("Count Chompula")
        val ARMOR_CHOMPER = Chomper("Armor Chomper")
        val CHOMP_THING = Chomper("Chomp Thing")
        val YETI_CHOMPER = Chomper("Yeti Chomper")
        val DISCO_CHOMPER = Chomper("Disco Chomper")

        // Sunflower characters
        val SUNFLOWER = Sunflower("Sunflower")
        val MYSTIC_FLOWER = Sunflower("Mystic Flower")
        val FIRE_FLOWER = Sunflower("Fire Flower")
        val POWER_FLOWER = Sunflower("Power Flower")
        val SHADOW_FLOWER = Sunflower("Shadow Flower")
        val METAL_PETAL = Sunflower("Metal Petal")
        val SUN_PHARAOH = Sunflower("Sun Pharaoh")
        val ALIEN_FLOWER = Sunflower("Alien Flower")
        val VAMPIRE_FLOWER = Sunflower("Vampire Flower")
        val STUFFY_FLOWER = Sunflower("Stuffy Flower")

        // Cacti characters
        val CACTUS = Cactus("Cactus")
        val CAMO_CACTUS = Cactus("Camo Cactus")
        val FIRE_CACTUS = Cactus("Fire Cactus")
        val ICE_CACTUS = Cactus("Ice Cactus")
        val FUTURE_CACTUS = Cactus("Future Cactus")
        val BANDIT_CACTUS = Cactus("Bandit Cactus")
        val JADE_CACTUS = Cactus("Jade Cactus")
        val POWER_CACTUS = Cactus("Power Cactus")
        val ZEN_CACTUS = Cactus("Zen Cactus")
        val PETRIFIED_CACTUS = Cactus("Petrified Cactus")

        // Torchwood character
        val TORCHWOOD = Torchwood("Torchwood")

        // Imp characters
        val IMP = Imp("Imp")
        val Z7_IMP = Imp("Z7 Imp")
        val PYLON_IMP = Imp("Pylon Imp")
        val LIL_DRAKE = Imp("Lil' Drake")
        val SHRIMP = Imp("S.H.R.IMP")
        val PARTY_IMP = Imp("Party Imp")
        val SCALLYWAG_IMP = Imp("Scallywag Imp")

        // SuperBrainz characters
        val SUPER_BRAINZ = SuperBrainz("Super Brainz")
        val ELECTRO_BRAINZ = SuperBrainz("Electro Brainz")
        val COZMIC_BRAINZ = SuperBrainz("Cozmic Brainz")
        val TOXIC_BRAINZ = SuperBrainz("Toxic Brainz")
        val PARTY_BRAINZ = SuperBrainz("Party Brainz")
        val BREAKFAST_BRAINZ = SuperBrainz("Breakfast Brainz")

        // Captain characters
        val CAPTAIN = Captain("Captain Deadbeard")
        val CAPTAIN_FLAMEFACE = Captain("Captain Flameface")
        val CAPTAIN_SHARKBITE = Captain("Captain Sharkbite")
        val CAPTAIN_CANNON = Captain("Captain Cannon")
        val CAPTAIN_PARTYMAN = Captain("Captain Partyman")
        val CAPTAIN_SQUAWK = Captain("Captain Squawk")

        // Soldier characters
        val SOLDIER = Soldier("Soldier")
        val SUPER_COMMANDO = Soldier("Super Commando")
        val ARCTIC_TROOPER = Soldier("Arctic Trooper")
        val GENERAL_SUPREMO = Soldier("General Supremo")
        val TANK_COMMANDER = Soldier("Tank Commander")
        val CAMO_RANGER = Soldier("Camo Ranger")
        val SKY_TROOPER = Soldier("Sky Trooper")
        val CENTURION = Soldier("Centurion")
        val PARK_RANGER = Soldier("Park Ranger")
        val SCUBA_SOLDIER = Soldier("Scuba Soldier")

        // Engineer characters
        val ENGINEER = Engineer("Engineer")
        val WELDER = Engineer("Welder")
        val PAINTER = Engineer("Painter")
        val MECHANIC = Engineer("Mechanic")
        val ELECTRICIAN = Engineer("Electrician")
        val PLUMBER = Engineer("Plumber")
        val LANDSCAPER = Engineer("Landscaper")
        val SANITATION_EXPERT = Engineer("Sanitation Expert")
        val ROADIE_Z = Engineer("Roadie Z")
        val AC_PERRY = Engineer("AC Perry")

        // Scientist characters
        val SCIENTIST = Scientist("Scientist")
        val CHEMIST = Scientist("Chemist")
        val PHYSICIST = Scientist("Physicist")
        val DR_TOXIC = Scientist("Dr. Toxic")
        val ASTRONAUT = Scientist("Astronaut")
        val MARINE_BIOLOGIST = Scientist("Marine Biologist")
        val ARCHEOLOGIST = Scientist("Archaeologist")
        val PALEONTOLOGIST = Scientist("Paleontologist")
        val ZOOLOGIST = Scientist("Zoologist")
        val COMPUTER_SCIENTIST = Scientist("Computer Scientist")

        // AllStar characters
        val ALL_STAR = AllStar("All Star")
        val BASEBALL_STAR = AllStar("Baseball Star")
        val HOCKEY_STAR = AllStar("Hockey Star")
        val RUGBY_STAR = AllStar("Rugby Star")
        val CRICKET_STAR = AllStar("Cricket Star")
        val GOALIE_STAR = AllStar("Goalie Star")
        val WRESTLING_STAR = AllStar("Wrestling Star")
        val TENNIS_STAR = AllStar("Tennis Star")
        val GOLF_STAR = AllStar("Golf Star")
        val MOTO_X_STAR = AllStar("Moto-X Star")

        // HoverGoat character
        val HOVER_GOAT = HoverGoat("Hover Goat")

        val allPlants = listOf(
            CITRON, IRON_CITRON, ELECTRO_CITRON, FROZEN_CITRON, PARTY_CITRON, TOXIC_CITRON,
            ROSE, DRUID_ROSE, FIRE_ROSE, FROST_ROSE, PARTY_ROSE, NEC_ROSE,
            CORN, BBQ_CORN, MOB_COB, POPS_CORN, PARTY_CORN, COMMANDO_CORN,
            PEASHOOTER, FIRE_PEA, ICE_PEA, TOXIC_PEA, COMMANDO_PEA, AGENT_PEA, LAW_PEA, PLASMA_PEA, ROCK_PEA, ELECTRO_PEA,
            CHOMPER, FIRE_CHOMPER, HOT_ROD_CHOMPER, POWER_CHOMPER, TOXIC_CHOMPER, COUNT_CHOMPULA, ARMOR_CHOMPER, CHOMP_THING, YETI_CHOMPER, DISCO_CHOMPER,
            SUNFLOWER, MYSTIC_FLOWER, FIRE_FLOWER, POWER_FLOWER, SHADOW_FLOWER, METAL_PETAL, SUN_PHARAOH, ALIEN_FLOWER, VAMPIRE_FLOWER, STUFFY_FLOWER,
            CACTUS, CAMO_CACTUS, FIRE_CACTUS, ICE_CACTUS, FUTURE_CACTUS, BANDIT_CACTUS, JADE_CACTUS, POWER_CACTUS, ZEN_CACTUS, PETRIFIED_CACTUS,
            TORCHWOOD)
        val allZombies = listOf(IMP, Z7_IMP, PYLON_IMP, LIL_DRAKE, SHRIMP, PARTY_IMP, SCALLYWAG_IMP,
            SUPER_BRAINZ, ELECTRO_BRAINZ, COZMIC_BRAINZ, TOXIC_BRAINZ, PARTY_BRAINZ, BREAKFAST_BRAINZ,
            CAPTAIN, CAPTAIN_FLAMEFACE, CAPTAIN_SHARKBITE, CAPTAIN_CANNON, CAPTAIN_PARTYMAN, CAPTAIN_SQUAWK,
            SOLDIER, SUPER_COMMANDO, ARCTIC_TROOPER, GENERAL_SUPREMO, TANK_COMMANDER, CAMO_RANGER, SKY_TROOPER, CENTURION, PARK_RANGER, SCUBA_SOLDIER,
            ENGINEER, WELDER, PAINTER, MECHANIC, ELECTRICIAN, PLUMBER, LANDSCAPER, SANITATION_EXPERT, ROADIE_Z, AC_PERRY,
            SCIENTIST, CHEMIST, PHYSICIST, DR_TOXIC, ASTRONAUT, MARINE_BIOLOGIST, ARCHEOLOGIST, PALEONTOLOGIST, ZOOLOGIST, COMPUTER_SCIENTIST,
            ALL_STAR, BASEBALL_STAR, HOCKEY_STAR, RUGBY_STAR, CRICKET_STAR, GOALIE_STAR, WRESTLING_STAR, TENNIS_STAR, GOLF_STAR, MOTO_X_STAR,
            HOVER_GOAT)
        val allCharacters = allPlants + allZombies
    }
}
