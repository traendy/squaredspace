package de.traendy.spaceshooter.engine

class FrameRate(private val interval:Long) {

    private var lastFrame = System.currentTimeMillis()
    private var numberOfFrames = 0L


    fun isNextFrame(currentTime:Long): Boolean {
        val isNextFrame = (currentTime - lastFrame) >= interval
        if (isNextFrame) {
            lastFrame = currentTime
            numberOfFrames++
        }
        return isNextFrame
    }

    fun getNumberOfFrames(): Long {
        return numberOfFrames
    }

    fun getFrameRate(currentTime:Long): Int {
        val frameRate = 1000 / (currentTime - lastFrame)
        return frameRate.toInt()
    }
}