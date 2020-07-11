package de.traendy.spaceshooter.engine

interface EntityHolder<T: Entity> {

    fun getAllEntities():List<T>

    fun prepareEntityAddition(entities: List<T>)
    fun prepareEntityAddition(entity: T)

    fun executePreparedAddition()

    fun prepareEntityDeletion(entities: List<T>)
    fun prepareEntityDeletion(entity: T)

    fun executePreparedDeletion()

}