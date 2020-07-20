package de.traendy.spaceshooter.game


import java.util.*

class OldGameState(var startTime:Long): Observable() {

    val mineSpawningInterval= 750L
    val bossSpawningInterval = 60*1000*16/8L/8
    var running:Boolean = false
    private var points = 0
    private var gameTime = 0L
    var meteorSpawningInterval = 512L
    var projectileSpawningInterval = GameConfig.attackSpeed
    set(value) {
        if(field > 256){
            field = value
        }else if(value == GameConfig.attackSpeed){
            field = value
        }
    }
    var starSpawningInterval = 208L
    var powerUpSpawningInterval = 5008L




    fun timeSurvived():Long = gameTime

    fun lose(time:Long){
        StateMediator.progressState()
        running = false
        gameTime = time - startTime
        setChanged()
        notifyObservers()
    }

    private fun resetProjectileSpawner() {
        projectileSpawningInterval = GameConfig.attackSpeed
    }

    fun addPoint(){
        points++
    }

    fun addPoint(point:Int) {
        points += point
    }

    fun highScore():Long{
        return points + (System.currentTimeMillis() - startTime) / 1000
    }

    fun reset() {
        startTime = System.currentTimeMillis()
        gameTime = 0L
        running = true
        points = 0
        setChanged()
        notifyObservers()
        resetProjectileSpawner()
    }
}
