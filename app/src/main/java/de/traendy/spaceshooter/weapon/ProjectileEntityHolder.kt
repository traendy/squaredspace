package de.traendy.spaceshooter.weapon

import android.graphics.Canvas
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import de.traendy.spaceshooter.engine.Spawner
import de.traendy.spaceshooter.engine.getCircleInterpolator
import kotlin.random.Random

class ProjectileEntityHolder(private val spawner: Spawner):
    PrimitiveEntityHolder<Projectile>() {

    fun spawnProjectiles(spawnX:Int, spawnY:Int){
        if (spawner.spawn()) {
            val projectile = Projectile(
                spawnX.toFloat(),
                spawnY.toFloat() - 50,
                 getCircleInterpolator(Random.nextFloat(), 1f)
            )
            prepareEntityAddition(projectile)
        }
    }

    fun updateProjectiles(canvas: Canvas){
        getAllEntities().forEach { projectile ->
            if (!projectile.isAlive()) {
                prepareEntityDeletion(projectile)
            }else{
                projectile.updatePosition(0f, 0f)
                projectile.draw(canvas)
            }
        }
    }
}