package de.traendy.spaceshooter.engine

import org.mockito.Mockito.mock

object TestEntityFactory:EntityFactory<Entity> {
    override fun create(): Entity {
        return mock(Entity::class.java)
    }
}