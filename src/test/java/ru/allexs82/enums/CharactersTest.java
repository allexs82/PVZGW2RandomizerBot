package ru.allexs82.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CharactersTest {

    @Test
    void getRandomCharacter_Zombie() {
        for (int i = 0; i <= 9; ++i) {
            Assertions.assertSame(Characters.getRandomCharacter(Sides.ZOMBIES).getSide(), Sides.ZOMBIES);
        }
    }

    @Test
    void getRandomCharacter_Plant() {
        for (int i = 0; i <= 9; ++i) {
            Assertions.assertSame(Characters.getRandomCharacter(Sides.PLANTS).getSide(), Sides.PLANTS);
        }
    }

    @Test
    void getRandomCharacter_Any() {
        for (int i = 0; i <= 9; ++i) {
            Characters character = Characters.getRandomCharacter(Sides.ANY);
            Assertions.assertTrue(character.getSide() == Sides.PLANTS || character.getSide() == Sides.ZOMBIES);
        }
    }
}