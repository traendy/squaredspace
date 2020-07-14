package de.traendy.spaceshooter.game

import java.util.*

class GameState( var startTime:Long): Observable() {

    val mineSpawningInterval= 704L
    val bossSpawningInterval = 60*1000*16/8L/8
    var running:Boolean = false
    private var points = 0
    private var gameTime = 0L
    var meteorSpawningInterval = 704L
    var projectileSpawningInterval = 1008L
    set(value) {
        if(field > 256){
            field = value
        }else if(value == 1008L){
            field = value
        }
    }
    var starSpawningInterval = 208L
    var powerUpSpawningInterval = 5008L




    fun timeSurvived():Long = gameTime

    fun lose(time:Long){
        running = false
        gameTime = time - startTime
        setChanged()
        notifyObservers()
    }

    private fun resetProjectileSpawner() {
        projectileSpawningInterval = 1008L
    }

    fun addPoint(){
        points++
    }

    fun addPoint(point:Int) {
        points += point
    }

    fun highScore():Long{
        return points * 16 + (System.currentTimeMillis() - startTime) / 1000
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
