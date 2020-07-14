package de.traendy.spaceshooter.engine

import android.graphics.RectF

/**
 * Just checks if to Entities collided or not.
 */
class PrimitiveCollisionDetector:
    CollisionDetector {
    override fun collided(aggressor: Entity, target: Entity): Boolean {
        return RectF.intersects(aggressor.getCollisionBox(),target.getCollisionBox())
    }
}