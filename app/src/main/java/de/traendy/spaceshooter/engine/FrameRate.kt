package de.traendy.spaceshooter.engine

class FrameRate(private val interval:Long) {

    private var lastFrame = System.currentTimeMillis()

    fun isNextFrame(currentTime:Long): Boolean {
        val isNextFrame = (currentTime - lastFrame) >= interval
        if (isNextFrame) {
            lastFrame = currentTime
        }
        return isNextFrame
    }
}