package de.traendy.spaceshooter.engine

import android.graphics.RectF

class PrimitiveCollisionDetector:
    CollisionDetector {
    override fun collided(aggressor: Entity, target: Entity): Boolean {
        return RectF.intersects(aggressor.getCollisionBox(),target.getCollisionBox())
    }
}