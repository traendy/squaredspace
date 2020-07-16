package de.traendy.spaceshooter.engine

import android.graphics.RectF

/**
 * Just checks if to Entities collided or not.
 */
class PrimitiveCollisionDetector:
    CollisionDetector {
    override fun collided(aggressor: Entity, target: Entity): Boolean {
        val collided = false
        aggressor.getCollisionBox().forEach { aggressorBox ->
            target.getCollisionBox().forEach{targetBox->
                if(RectF.intersects(aggressorBox,targetBox)) return true
            }
        }
        return false
    }
}