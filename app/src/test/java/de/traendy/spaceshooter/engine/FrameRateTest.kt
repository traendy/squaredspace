package de.traendy.spaceshooter.engine

import org.junit.Test

import org.junit.Assert.*

class FrameRateTest {

    @Test
    fun isNextFrameTrue() {
        val rate = FrameRate(16)
        assertTrue(rate.isNextFrame(System.currentTimeMillis()+17))
    }

    @Test
    fun isNextFrameFalse() {
        val rate = FrameRate(16)
        assertFalse(rate.isNextFrame(System.currentTimeMillis()))
    }

    @Test
    fun isNextFrameZeroTrue() {
        val rate = FrameRate(0)
        assertTrue(rate.isNextFrame(System.currentTimeMillis()))
    }

    @Test
    fun isNextFrameZeroFalse() {
        val rate = FrameRate(0)
        assertFalse(rate.isNextFrame(System.currentTimeMillis() - 50L))
    }

    @Test
    fun isNextFrameNegativeFalse() {
        val rate = FrameRate(-10)
        assertFalse(rate.isNextFrame(System.currentTimeMillis() - 15L))
    }

    @Test
    fun isNextFrameNegativeTrue() {
        val rate = FrameRate(-10)
        assertTrue(rate.isNextFrame(System.currentTimeMillis() + 20L))
    }

    @Test
    fun isNextFrameMinValue() {
        val rate = FrameRate(Long.MIN_VALUE)
        assertTrue(rate.isNextFrame(System.currentTimeMillis()))
    }

    @Test
    fun isNextFrameMaxValue() {
        val rate = FrameRate(Long.MAX_VALUE)
        assertFalse(rate.isNextFrame(System.currentTimeMillis()))
    }
}