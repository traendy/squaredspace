package de.traendy.spaceshooter.engine

interface CollisionDetector{
    public fun collided(aggressor: Entity, target: Entity):Boolean
}