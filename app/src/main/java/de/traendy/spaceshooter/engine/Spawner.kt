package de.traendy.spaceshooter.engine


class Spawner(private var interval: Long) {

    private var lastSpawn = System.currentTimeMillis()
    private var enabled = true

    fun spawn(): Boolean {
        if(enabled) {
            val currentTime = System.currentTimeMillis()
            val spawn = (currentTime - lastSpawn) >= interval
            if (spawn) {
                lastSpawn = currentTime
            }
            return spawn
        }
        return false
    }


    fun enable(){
        if (!enabled) {
            enabled = true
            lastSpawn = System.currentTimeMillis()
        }
    }

    fun disable(){
        enabled = false
    }

    fun updateInterval(newInterval:Long){
        interval = newInterval
    }
}