package de.traendy.spaceshooter.effects

import android.graphics.Canvas
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import de.traendy.spaceshooter.engine.Spawner

class ExhaustEntitiyHolder(private val spawner: Spawner) : PrimitiveEntityHolder<Exhaust>() {

    public fun spawn(posX: Float, posY: Float) {
        if (spawner.spawn()) {
            prepareEntityAddition(Exhaust(posX + 50f, posY))
            prepareEntityAddition(Exhaust(posX - 50f, posY))
        }
    }

    public fun draw(canvas: Canvas) {
        getAllEntities().forEach {
            if (it.isAlive()) {
                it.updatePosition(0f, 0f)
                it.draw(canvas)
            }else{
                prepareEntityDeletion(it)
            }

        }
    }
}