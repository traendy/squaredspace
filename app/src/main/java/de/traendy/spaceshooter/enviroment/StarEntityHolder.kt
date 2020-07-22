package de.traendy.spaceshooter.enviroment

import android.graphics.Canvas
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import de.traendy.spaceshooter.engine.Spawner
import de.traendy.spaceshooter.game.GameConfig
import kotlin.random.Random

class StarEntityHolder(private val starSpawner: Spawner): PrimitiveEntityHolder<Star>() {

    fun spawnStars(worldHeight:Float, worldWidth:Float){
        if (starSpawner.spawn()) {
            for (i in 0..Random.nextInt(GameConfig.starSpawnRange)) {
                val star = Star(
                    worldHeight,
                    Random.nextInt(worldWidth.toInt()).toFloat()
                )
                prepareEntityAddition(star)
            }
        }
    }

    fun updateStars(canvas: Canvas){
        getAllEntities().forEach {
            it.updatePosition(0f, 0f)
            it.draw(canvas)
            if(!it.isAlive()) prepareEntityDeletion(it)
        }
    }
}