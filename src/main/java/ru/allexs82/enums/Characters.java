package ru.allexs82.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum Characters {
    CITRON("Citron", Classes.CITRON, Sides.PLANTS),
    IRON_CITRON("Iron Citron", Classes.CITRON, Sides.PLANTS),
    ELECTRO_CITRON("Electro Citron", Classes.CITRON, Sides.PLANTS),
    FROZEN_CITRON("Frozen Citron", Classes.CITRON, Sides.PLANTS),
    PARTY_CITRON("Party Citron", Classes.CITRON, Sides.PLANTS),
    TOXIC_CITRON("Toxic Citron", Classes.CITRON, Sides.PLANTS),

    ROSE("Rose", Classes.ROSE, Sides.PLANTS),
    DRUID_ROSE("Druid Rose", Classes.ROSE, Sides.PLANTS),
    FIRE_ROSE("Fire Rose", Classes.ROSE, Sides.PLANTS),
    FROST_ROSE("Frost Rose", Classes.ROSE, Sides.PLANTS),
    PARTY_ROSE("Party Rose", Classes.ROSE, Sides.PLANTS),
    NEC_ROSE("Nec'Rose", Classes.ROSE, Sides.PLANTS),

    CORN("Kernel Corn", Classes.CORN, Sides.PLANTS),
    BBQ_CORN("BBQ Corn", Classes.CORN, Sides.PLANTS),
    MOB_COB("Mob Cob", Classes.CORN, Sides.PLANTS),
    POPS_CORN("Pops Corn", Classes.CORN, Sides.PLANTS),
    PARTY_CORN("Party Corn", Classes.CORN, Sides.PLANTS),
    COMMANDO_CORN("Commando Corn", Classes.CORN, Sides.PLANTS),

    PEASHOOTER("Peashooter", Classes.PEASHOOTER, Sides.PLANTS),
    FIRE_PEA("Fire Pea", Classes.PEASHOOTER, Sides.PLANTS),
    ICE_PEA("Ice Pea", Classes.PEASHOOTER, Sides.PLANTS),
    TOXIC_PEA("Toxic Pea", Classes.PEASHOOTER, Sides.PLANTS),
    COMMANDO_PEA("Commando Pea", Classes.PEASHOOTER, Sides.PLANTS),
    AGENT_PEA("Agent Pea", Classes.PEASHOOTER, Sides.PLANTS),
    LAW_PEA("Law Pea", Classes.PEASHOOTER, Sides.PLANTS),
    PLASMA_PEA("Plasma Pea", Classes.PEASHOOTER, Sides.PLANTS),
    ROCK_PEA("Rock Pea", Classes.PEASHOOTER, Sides.PLANTS),
    ELECTRO_PEA("Electro Pea", Classes.PEASHOOTER, Sides.PLANTS),

    CHOMPER("Chomper", Classes.CHOMPER, Sides.PLANTS),
    FIRE_CHOMPER("Fire Chomper", Classes.CHOMPER, Sides.PLANTS),
    HOT_ROD_CHOMPER("Hot Rod Chomper", Classes.CHOMPER, Sides.PLANTS),
    POWER_CHOMPER("Power Chomper", Classes.CHOMPER, Sides.PLANTS),
    TOXIC_CHOMPER("Toxic Chomper", Classes.CHOMPER, Sides.PLANTS),
    COUNT_CHOMPULA("Count Chompula", Classes.CHOMPER, Sides.PLANTS),
    ARMOR_CHOMPER("Armor Chomper", Classes.CHOMPER, Sides.PLANTS),
    CHOMP_THING("Chomp Thing", Classes.CHOMPER, Sides.PLANTS),
    YETI_CHOMPER("Yeti Chomper", Classes.CHOMPER, Sides.PLANTS),
    DISCO_CHOMPER("Disco Chomper", Classes.CHOMPER, Sides.PLANTS),

    SUNFLOWER("Sunflower", Classes.SUNFLOWER, Sides.PLANTS),
    MYSTIC_FLOWER("Mystic Flower", Classes.SUNFLOWER, Sides.PLANTS),
    FIRE_FLOWER("Fire Flower", Classes.SUNFLOWER, Sides.PLANTS),
    POWER_FLOWER("Power Flower", Classes.SUNFLOWER, Sides.PLANTS),
    SHADOW_FLOWER("Shadow Flower", Classes.SUNFLOWER, Sides.PLANTS),
    METAL_PETAL("Metal Petal", Classes.SUNFLOWER, Sides.PLANTS),
    SUN_PHARAOH("Sun Pharaoh", Classes.SUNFLOWER, Sides.PLANTS),
    ALIEN_FLOWER("Alien Flower", Classes.SUNFLOWER, Sides.PLANTS),
    VAMPIRE_FLOWER("Vampire Flower", Classes.SUNFLOWER, Sides.PLANTS),
    STUFFY_FLOWER("Stuffy Flower", Classes.SUNFLOWER, Sides.PLANTS),

    CACTUS("Cactus", Classes.CACTUS, Sides.PLANTS),
    CAMO_CACTUS("Camo Cactus", Classes.CACTUS, Sides.PLANTS),
    FIRE_CACTUS("Fire Cactus", Classes.CACTUS, Sides.PLANTS),
    ICE_CACTUS("Ice Cactus", Classes.CACTUS, Sides.PLANTS),
    FUTURE_CACTUS("Future Cactus", Classes.CACTUS, Sides.PLANTS),
    BANDIT_CACTUS("Bandit Cactus", Classes.CACTUS, Sides.PLANTS),
    JADE_CACTUS("Jade Cactus", Classes.CACTUS, Sides.PLANTS),
    POWER_CACTUS("Power Cactus", Classes.CACTUS, Sides.PLANTS),
    ZEN_CACTUS("Zen Cactus", Classes.CACTUS, Sides.PLANTS),
    PETRIFIED_CACTUS("Petrified Cactus", Classes.CACTUS, Sides.PLANTS),

    TORCHWOOD("Torchwood", Classes.TORCHWOOD, Sides.PLANTS),

    IMP("Imp", Classes.IMP, Sides.ZOMBIES),
    Z7_IMP("Z7 Imp", Classes.IMP, Sides.ZOMBIES),
    PYLON_IMP("Pylon Imp", Classes.IMP, Sides.ZOMBIES),
    LIL_DRAKE("Lil' Drake", Classes.IMP, Sides.ZOMBIES),
    SHRIMP("S.H.R.IMP", Classes.IMP, Sides.ZOMBIES),
    PARTY_IMP("Party Imp", Classes.IMP, Sides.ZOMBIES),
    SCALLYWAG_IMP("Scallywag Imp", Classes.IMP, Sides.ZOMBIES),

    SUPER_BRAINZ("Super Brainz", Classes.SUPER_BRAINZ, Sides.ZOMBIES),
    ELECTRO_BRAINZ("Electro Brainz", Classes.SUPER_BRAINZ, Sides.ZOMBIES),
    COZMIC_BRAINZ("Cozmic Brainz", Classes.SUPER_BRAINZ, Sides.ZOMBIES),
    TOXIC_BRAINZ("Toxic Brainz", Classes.SUPER_BRAINZ, Sides.ZOMBIES),
    PARTY_BRAINZ("Party Brainz", Classes.SUPER_BRAINZ, Sides.ZOMBIES),
    BREAKFAST_BRAINZ("Breakfast Brainz", Classes.SUPER_BRAINZ, Sides.ZOMBIES),

    CAPTAIN("Captain Deadbeard", Classes.CAPTAIN, Sides.ZOMBIES),
    CAPTAIN_FLAMEFACE("Captain Flameface", Classes.CAPTAIN, Sides.ZOMBIES),
    CAPTAIN_SHARKBITE("Captain Sharkbite", Classes.CAPTAIN, Sides.ZOMBIES),
    CAPTAIN_CANNON("Captain Cannon", Classes.CAPTAIN, Sides.ZOMBIES),
    CAPTAIN_PARTYMAN("Captain Partyman", Classes.CAPTAIN, Sides.ZOMBIES),
    CAPTAIN_SQUAWK("Captain Squawk", Classes.CAPTAIN, Sides.ZOMBIES),

    SOLDIER("Soldier", Classes.SOLDIER, Sides.ZOMBIES),
    SUPER_COMMANDO("Super Commando", Classes.SOLDIER, Sides.ZOMBIES),
    ARCTIC_TROOPER("Arctic Trooper", Classes.SOLDIER, Sides.ZOMBIES),
    GENERAL_SUPREMO("General Supremo", Classes.SOLDIER, Sides.ZOMBIES),
    TANK_COMMANDER("Tank Commander", Classes.SOLDIER, Sides.ZOMBIES),
    CAMO_RANGER("Camo Ranger", Classes.SOLDIER, Sides.ZOMBIES),
    SKY_TROOPER("Sky Trooper", Classes.SOLDIER, Sides.ZOMBIES),
    CENTURION("Centurion", Classes.SOLDIER, Sides.ZOMBIES),
    PARK_RANGER("Park Ranger", Classes.SOLDIER, Sides.ZOMBIES),
    SCUBA_SOLDIER("Scuba Soldier", Classes.SOLDIER, Sides.ZOMBIES),

    ENGINEER("Engineer", Classes.ENGINEER, Sides.ZOMBIES),
    WELDER("Welder", Classes.ENGINEER, Sides.ZOMBIES),
    PAINTER("Painter", Classes.ENGINEER, Sides.ZOMBIES),
    MECHANIC("Mechanic", Classes.ENGINEER, Sides.ZOMBIES),
    ELECTRICIAN("Electrician", Classes.ENGINEER, Sides.ZOMBIES),
    PLUMBER("Plumber", Classes.ENGINEER, Sides.ZOMBIES),
    LANDSCAPER("Landscaper", Classes.ENGINEER, Sides.ZOMBIES),
    SANITATION_EXPERT("Sanitation Expert", Classes.ENGINEER, Sides.ZOMBIES),
    ROADIE_Z("Roadie Z", Classes.ENGINEER, Sides.ZOMBIES),
    AC_PERRY("AC Perry", Classes.ENGINEER, Sides.ZOMBIES),

    SCIENTIST("Scientist", Classes.SCIENTIST, Sides.ZOMBIES),
    CHEMIST("Chemist", Classes.SCIENTIST, Sides.ZOMBIES),
    PHYSICIST("Physicist", Classes.SCIENTIST, Sides.ZOMBIES),
    DR_TOXIC("Dr. Toxic", Classes.SCIENTIST, Sides.ZOMBIES),
    ASTRONAUT("Astronaut", Classes.SCIENTIST, Sides.ZOMBIES),
    MARINE_BIOLOGIST("Marine Biologist", Classes.SCIENTIST, Sides.ZOMBIES),
    ARCHEOLOGIST("Archaeologist", Classes.SCIENTIST, Sides.ZOMBIES),
    PALEONTOLOGIST("Paleontologist", Classes.SCIENTIST, Sides.ZOMBIES),
    ZOOLOGIST("Zoologist", Classes.SCIENTIST, Sides.ZOMBIES),
    COMPUTER_SCIENTIST("Computer Scientist", Classes.SCIENTIST, Sides.ZOMBIES),

    ALL_STAR("All Star", Classes.ALL_STAR, Sides.ZOMBIES),
    BASEBALL_STAR("Baseball Star", Classes.ALL_STAR, Sides.ZOMBIES),
    HOCKEY_STAR("Hockey Star", Classes.ALL_STAR, Sides.ZOMBIES),
    RUGBY_STAR("Rugby Star", Classes.ALL_STAR, Sides.ZOMBIES),
    CRICKET_STAR("Cricket Star", Classes.ALL_STAR, Sides.ZOMBIES),
    GOALIE_STAR("Goalie Star", Classes.ALL_STAR, Sides.ZOMBIES),
    WRESTLING_STAR("Wrestling Star", Classes.ALL_STAR, Sides.ZOMBIES),
    TENNIS_STAR("Tennis Star", Classes.ALL_STAR, Sides.ZOMBIES),
    GOLF_STAR("Golf Star", Classes.ALL_STAR, Sides.ZOMBIES),
    MOTO_X_STAR("Moto-X Star", Classes.ALL_STAR, Sides.ZOMBIES),

    HOVER_GOAT("Hover Goat", Classes.HOVER_GOAT, Sides.ZOMBIES);

    private static final Random RANDOM = new Random();

    private final String name;
    private final Classes charClass;
    private final Sides side;

    Characters(String name, Classes charClass, Sides side) {
        this.name = name;
        this.charClass = charClass;
        this.side = side;
    }

    /**
     * Returns a random character for the given side.
     *
     * @param side the side to filter characters by
     * @return a random character for the specified side
     */
    public static Characters getRandomCharacter(Sides side) {
        List<Characters> matchingChars;
        if (side == Sides.ANY) {
            matchingChars = Arrays.asList(Characters.values());
        } else {
            matchingChars = Arrays.stream(Characters.values())
                    .filter(character -> character.getSide().equals(side))
                    .collect(Collectors.toList());
        }
        for (int i = 0; i < 10; ++i) {
            Collections.shuffle(matchingChars, RANDOM);
        }
        return matchingChars.get(RANDOM.nextInt(matchingChars.size()));
    }

    public String getName() {
        return name;
    }

    public Classes getCharClass() {
        return charClass;
    }

    public Sides getSide() {
        return side;
    }
}
