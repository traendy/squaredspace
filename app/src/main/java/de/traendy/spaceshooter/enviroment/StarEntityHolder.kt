package de.traendy.spaceshooter.enviroment

import android.graphics.Canvas
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import de.traendy.spaceshooter.engine.Spawner
import java.util.*

class StarEntityHolder(private val starSpawner: Spawner): PrimitiveEntityHolder<Star>() {

    fun spawnStars(maxX:Int, maxY:Int){
        if (starSpawner.spawn()) {
            for (i in 0..Random().nextInt(3)) {
                val star = Star(
                    maxX,
                    kotlin.random.Random.nextInt(maxY)
                )
                prepareEntityAddition(star)
            }
//            executePreparedAddition()
        }
    }

    fun updateStars(canvas: Canvas){
        getAllEntities().forEach {
            it.updatePosition(0, 0)
            it.draw(canvas)
            if(!it.isAlive()) prepareEntityDeletion(it)
        }
//        executePreparedDeletion()
    }
}