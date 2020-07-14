package de.traendy.spaceshooter.player

import org.junit.Test

import org.junit.Assert.*


class PlayerTest {

    @Test
    fun setSpawn() {
        val player = TestPlayerFactory.create()
        player.setSpawn(10f, 10f)
        assertEquals(player.xPos, 10)
        assertEquals(player.yPos, 10)
    }

    @Test
    fun updatePosition() {
        val player = TestPlayerFactory.create()
        player.setSpawn(10f, 10f)
        player.updatePosition(1f, 1f )
        assertEquals(10, player.xPos)
        assertEquals(10, player.yPos)
    }

    @Test
    fun inBounds() {
        val player = TestPlayerFactory.create()
        player.setSpawn(10f, 10f)
        assertTrue(player.inBounds(0f,0f))
        assertFalse(player.inBounds(1f,0f))
        assertFalse(player.inBounds(0f,1f))
        assertFalse(player.inBounds(1f,1f))
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