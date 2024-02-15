package de.traendy.spaceshooter.game

object StateMediator {
    private var state: GameState<Unit> = Menu
    private val listeners = mutableListOf<Listener>()
    fun getState(): GameState<Unit> = state

    fun progressState() {
        state = state.handle(Unit)
        listeners.forEach { it.onStateChange() }
    }

    fun register(listener: Listener) {
        listeners.add(listener)
    }


    interface Listener {
        fun onStateChange()
    }
}