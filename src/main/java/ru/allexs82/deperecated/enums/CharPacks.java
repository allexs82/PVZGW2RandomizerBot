package ru.allexs82.deperecated.enums;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Enum representing character packs with associated characters.
 */
@Deprecated
public enum CharPacks {
    FIRST(Characters.FIRE_FLOWER, Characters.YETI_CHOMPER, Characters.CAPTAIN_SQUAWK, Characters.CAPTAIN_SHARKBITE),
    SECOND(Characters.TOXIC_PEA, Characters.TOXIC_CITRON, Characters.SHRIMP, Characters.COZMIC_BRAINZ),
    THIRD(Characters.FIRE_PEA, Characters.FIRE_CHOMPER, Characters.LIL_DRAKE, Characters.CAPTAIN_CANNON),
    FOURTH(Characters.CORN, Characters.PETRIFIED_CACTUS, Characters.SUPER_COMMANDO, Characters.BREAKFAST_BRAINZ),
    FIFTH(Characters.ALIEN_FLOWER, Characters.CITRON, Characters.CAPTAIN_FLAMEFACE, Characters.SUPER_COMMANDO),
    SIXTH(Characters.PEASHOOTER, Characters.COMMANDO_PEA, Characters.MARINE_BIOLOGIST, Characters.ARCHEOLOGIST),
    SEVENTH(Characters.CAMO_CACTUS, Characters.FROST_ROSE, Characters.CAPTAIN_PARTYMAN, Characters.ELECTRO_BRAINZ),
    EIGHT(Characters.AGENT_PEA, Characters.CHOMP_THING, Characters.ASTRONAUT, Characters.ARCHEOLOGIST),
    NINTH(Characters.POWER_FLOWER, Characters.ELECTRO_PEA, Characters.SCUBA_SOLDIER, Characters.ROADIE_Z),
    TENTH(Characters.DRUID_ROSE, Characters.TOXIC_CHOMPER, Characters.IMP, Characters.PLUMBER),
    ELEVENTH(Characters.BBQ_CORN, Characters.FROZEN_CITRON, Characters.HOCKEY_STAR, Characters.PYLON_IMP),
    TWELFTH(Characters.FUTURE_CACTUS, Characters.FIRE_ROSE, Characters.DR_TOXIC, Characters.WRESTLING_STAR),
    THIRTEENTH(Characters.CACTUS, Characters.ROCK_PEA, Characters.ALL_STAR, Characters.SKY_TROOPER),
    FOURTEENTH(Characters.PARTY_CORN, Characters.PARTY_ROSE, Characters.COMPUTER_SCIENTIST, Characters.PARTY_BRAINZ),
    FIFTEENTH(Characters.ZEN_CACTUS, Characters.PARTY_CITRON, Characters.PARK_RANGER, Characters.AC_PERRY),
    SIXTEENTH(Characters.PLASMA_PEA, Characters.ELECTRO_CITRON, Characters.SOLDIER, Characters.GOLF_STAR),
    SEVENTEENTH(Characters.COMMANDO_CORN, Characters.CHOMPER, Characters.ARCTIC_TROOPER, Characters.PALEONTOLOGIST),
    EIGHTEENTH(Characters.LAW_PEA, Characters.JADE_CACTUS, Characters.GOALIE_STAR, Characters.MECHANIC),
    NINETEENTH(Characters.VAMPIRE_FLOWER, Characters.IRON_CITRON, Characters.PAINTER, Characters.ZOOLOGIST),
    TWENTIETH(Characters.STUFFY_FLOWER, Characters.ROSE, Characters.LANDSCAPER, Characters.RUGBY_STAR),
    TWENTY_FIRST(Characters.SUNFLOWER, Characters.POPS_CORN, Characters.CHEMIST, Characters.TANK_COMMANDER),
    TWENTY_SECOND(Characters.POWER_CACTUS, Characters.POWER_CHOMPER, Characters.WELDER, Characters.HOVER_GOAT),
    TWENTY_THIRD(Characters.MOB_COB, Characters.BANDIT_CACTUS, Characters.CAMO_RANGER, Characters.ELECTRICIAN),
    TWENTY_FOURTH(Characters.MOB_COB, Characters.BANDIT_CACTUS, Characters.CAMO_RANGER, Characters.ELECTRICIAN),
    TWENTY_FIFTH(Characters.FIRE_CACTUS, Characters.NEC_ROSE, Characters.SUPER_BRAINZ, Characters.TENNIS_STAR),
    TWENTY_SIXTH(Characters.COUNT_CHOMPULA, Characters.ICE_PEA, Characters.CENTURION, Characters.SCIENTIST),
    TWENTY_SEVENTH(Characters.DISCO_CHOMPER, Characters.ICE_CACTUS, Characters.CAPTAIN, Characters.PARTY_IMP),
    TWENTY_EIGHTH(Characters.TORCHWOOD, Characters.ARMOR_CHOMPER, Characters.Z7_IMP, Characters.MOTO_X_STAR),
    TWENTY_NINTH(Characters.SHADOW_FLOWER, Characters.HOT_ROD_CHOMPER, Characters.TOXIC_BRAINZ, Characters.ENGINEER),
    THIRTIETH(Characters.METAL_PETAL, Characters.SUN_PHARAOH, Characters.CRICKET_STAR, Characters.PHYSICIST),
    THIRTY_FIRST(Characters.MYSTIC_FLOWER, Characters.SCALLYWAG_IMP, Characters.SANITATION_EXPERT),

    THIRTY_SECOND(Characters.CAMO_CACTUS, Characters.PETRIFIED_CACTUS, Characters.ELECTRO_PEA, Characters.CAMO_RANGER,
            Characters.CAPTAIN_CANNON, Characters.CAPTAIN_SQUAWK),
    THIRTY_THIRD(Characters.AGENT_PEA, Characters.TOXIC_CITRON, Characters.ARMOR_CHOMPER, Characters.FROST_ROSE,
            Characters.TOXIC_BRAINZ, Characters.PARTY_BRAINZ, Characters.ELECTRO_BRAINZ, Characters.COZMIC_BRAINZ),
    THIRTY_FOURTH(Characters.TORCHWOOD, Characters.YETI_CHOMPER, Characters.FIRE_ROSE, Characters.CHOMP_THING,
            Characters.ALL_STAR, Characters.ZOOLOGIST, Characters.ROADIE_Z, Characters.BREAKFAST_BRAINZ),
    THIRTY_FIFTH(Characters.SOLDIER, Characters.PAINTER, Characters.ARCHEOLOGIST, Characters.ROADIE_Z,
            Characters.FUTURE_CACTUS, Characters.PLASMA_PEA, Characters.CHOMP_THING, Characters.ELECTRO_CITRON),
    THIRTY_SIXTH(Characters.AGENT_PEA, Characters.TORCHWOOD, Characters.POWER_CHOMPER, Characters.TOXIC_CHOMPER,
            Characters.FIRE_CHOMPER, Characters.HOCKEY_STAR, Characters.SUPER_COMMANDO, Characters.COZMIC_BRAINZ,
            Characters.ELECTRO_BRAINZ, Characters.BREAKFAST_BRAINZ);

    private final List<Characters> characters;

    CharPacks(Characters... characters) {
        this.characters = Arrays.asList(characters);
    }

    /**
     * Gets the list of characters in the pack.
     *
     * @return an unmodifiable list of characters
     */
    @NotNull
    @Contract(pure = true)
    public List<Characters> getCharacters() {
        return new ArrayList<>(characters);
    }

    /**
     * Converts the character pack to a MessageEmbed.
     *
     * @return a MessageEmbed representing the character pack
     */
    @NotNull
    public MessageEmbed toEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(0xffaec9);
        for (Characters character : characters) {
            embedBuilder.appendDescription(character.getName() + "\n");
        }
        return embedBuilder.build();
    }
}
