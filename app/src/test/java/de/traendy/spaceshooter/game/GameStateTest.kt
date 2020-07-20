package de.traendy.spaceshooter.game

import org.junit.Assert.*
import org.junit.Test

class GameStateTest {

    @Test
    fun walkThroughStates() {
        assertTrue(Menu.handle(Unit) is GameStart)
        assertFalse(Menu.handle(Unit) is GameEnd)
        assertTrue(Menu.handle(Unit).handle(Unit) is GameRunning)
        assertTrue(Menu.handle(Unit).handle(Unit).handle(Unit) is GameEnd)
        assertTrue(Menu.handle(Unit).handle(Unit).handle(Unit).handle(Unit) is Menu)
    }
}