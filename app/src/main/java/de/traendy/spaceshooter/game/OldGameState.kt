package de.traendy.spaceshooter.game


import java.util.*

class OldGameState(var startTime:Long): Observable() {

    var running:Boolean = false
    private var points = 0
    private var gameTime = 0L
    var projectileSpawningInterval = GameConfig.attackSpeed
    set(value) {
        if(field > GameConfig.maxAttackSpeed){
            field = value
        }else if(value == GameConfig.attackSpeed){
            field = value
        }
    }


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
