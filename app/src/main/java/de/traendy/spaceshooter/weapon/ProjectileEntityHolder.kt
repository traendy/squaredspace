package de.traendy.spaceshooter.weapon

import android.graphics.Canvas
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import de.traendy.spaceshooter.engine.Spawner
import kotlin.random.Random

class ProjectileEntityHolder(private val spawner: Spawner):
    PrimitiveEntityHolder<Projectile>() {

    fun spawnProjectiles(spawnY:Int, spawnX:Int){
        if (spawner.spawn()) {
            val projectile = Projectile(
                spawnY.toFloat(),
                spawnX.toFloat(),
                Random.nextInt(-2, 3)
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