package de.traendy.spaceshooter.game

object StateMediator {
    private var state: GameState<Unit> = Menu
    private val listeners = mutableListOf<Listener>()
    public fun getState(): GameState<Unit> = state

    public fun progressState() {
        state = state.handle(Unit)
        listeners.forEach { it.onStateChange() }
    }

    public fun register(listener: Listener) {
        listeners.add(listener)
    }


    interface Listener {
        fun onStateChange()
    }
}