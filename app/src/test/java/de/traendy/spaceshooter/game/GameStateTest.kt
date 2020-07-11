package de.traendy.spaceshooter.game

import de.traendy.spaceshooter.game.GameState
import org.junit.Test

import org.junit.Assert.*

class GameStateTest {

    @Test
    fun isRunning() {
    }

    @Test
    fun getGameTime() {
    }

    @Test
    fun lose() {
        val state = GameState(33)
        state.lose(34)
        assertEquals(1, state.timeSurvived())
    }
}