package de.traendy.spaceshooter.game

import android.util.Log

interface GameState<T> {


    abstract fun handle(info:T):GameState<T>
}

object Menu:GameState<Unit> {
    override fun handle(info: Unit): GameState<Unit> {
        Log.d("GameState", "Menu")
        return GameStart
    }
}

object GameStart : GameState<Unit> {
    override fun handle(info: Unit): GameState<Unit> {
        Log.d("GameState", "GameStart")
        return GameRunning
    }
}

object GameRunning : GameState<Unit> {
    override fun handle(info: Unit): GameState<Unit> {
        Log.d("GameState", "GameRunning")
        return GameEnd
    }
}

object GameEnd : GameState<Unit> {
    override fun handle(info: Unit): GameState<Unit> {
        Log.d("GameState", "GameEnd")
        return Menu
    }
}