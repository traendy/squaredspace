package de.traendy.spaceshooter.game

import org.junit.Test

import org.junit.Assert.*

class OldGameStateTest {

    @Test
    fun isRunning() {
    }

    @Test
    fun getGameTime() {
    }

    @Test
    fun lose() {
        val state = OldGameState(33)
        state.lose(34)
        assertEquals(1, state.timeSurvived())
    }
}