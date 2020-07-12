package de.traendy.spaceshooter.engine

interface CollisionDetector{
    fun collided(aggressor: Entity, target: Entity):Boolean
}