package de.traendy.spaceshooter.engine

interface EntityFactory<T: Entity> {

    fun create():T
}