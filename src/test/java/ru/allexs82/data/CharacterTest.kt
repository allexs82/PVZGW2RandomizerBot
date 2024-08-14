package ru.allexs82.data

import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import ru.allexs82.data.enums.Sides

class CharacterTest {
    @Test
    fun getRandomCharacter_Zombie() {
        for (i in 0..9) {
            assertSame(Character.getRandomCharacter(Sides.ZOMBIES).side, Sides.ZOMBIES)
        }
    }

    @Test
    fun getRandomCharacter_Plant() {
        for (i in 0..9) {
            assertSame(Character.getRandomCharacter(Sides.PLANTS).side, Sides.PLANTS)
        }
    }

    @Test
    fun getRandomCharacter_Any() {
        for (i in 0..9) {
            val character = Character.getRandomCharacter(Sides.ANY)
            assertTrue(character.side == Sides.PLANTS || character.side == Sides.ZOMBIES)
        }
    }
}
