package de.traendy.spaceshooter.engine

import org.junit.Test

import org.junit.Assert.*

class SpawnerTest {

    @Test
    fun spawn() {
        val spawner = Spawner(0)
        assertTrue(spawner.spawn())
    }

    @Test
    fun enable() {
        val spawner = Spawner(0)
        assertTrue(spawner.spawn())
        spawner.disable()
        assertFalse(spawner.spawn())
        spawner.enable()
        assertTrue(spawner.spawn())
    }

    @Test
    fun disable() {
        val spawner = Spawner(0)
        assertTrue(spawner.spawn())
        spawner.disable()
        assertFalse(spawner.spawn())
    }
}