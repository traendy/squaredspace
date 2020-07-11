package de.traendy.spaceshooter.player

import org.junit.Test

import org.junit.Assert.*


class PlayerTest {

    @Test
    fun setSpawn() {
        val player = TestPlayerFactory.create()
        player.setSpawn(10, 10)
        assertEquals(player._X, 10)
        assertEquals(player._Y, 10)
    }

    @Test
    fun updatePosition() {
        val player = TestPlayerFactory.create()
        player.setSpawn(10, 10)
        player.updatePosition(1, 1 )
        assertEquals(10, player._X)
        assertEquals(10, player._Y)
    }

    @Test
    fun inBounds() {
        val player = TestPlayerFactory.create()
        player.setSpawn(10, 10)
        assertTrue(player.inBounds(0,0))
        assertFalse(player.inBounds(1,0))
        assertFalse(player.inBounds(0,1))
        assertFalse(player.inBounds(1,1))
    }


    @Test
    fun getCollisionBox() {
        val player= TestPlayerFactory.create()
        assertNotNull(player.getCollisionBox())
    }

    @Test
    fun isAlive() {
        val player = TestPlayerFactory.create()
        assertTrue(player.isAlive())
        player.kill()
        assertFalse(player.isAlive())
    }

    @Test
    fun kill() {
        val player = TestPlayerFactory.create()
        assertTrue(player.isAlive())
        player.kill()
        assertFalse(player.isAlive())
    }
}