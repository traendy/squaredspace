package de.traendy.spaceshooter.engine

class FrameRate {
    private val interval = 16
    private var lastFrame = System.currentTimeMillis()

    fun isNextFrame(): Boolean {
        val currentTime = System.currentTimeMillis()
        val isNextFrame = (currentTime - lastFrame) >= interval
        if (isNextFrame) {
            lastFrame = currentTime
        }
        return isNextFrame
    }
}