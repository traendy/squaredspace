package de.traendy.spaceshooter.engine

open class PrimitiveEntityHolder<T: Entity>:
    EntityHolder<T> {

    private val currentEntities = mutableListOf<T>()
    private val additionEntities = mutableListOf<T>()
    private val deletionEntities = mutableListOf<T>()

    override fun getAllEntities(): List<T> {
        return currentEntities;
    }

    override fun prepareEntityAddition(entities: List<T>) {
        additionEntities.addAll(entities)
    }

    override fun prepareEntityAddition(entity: T) {
        additionEntities.add(entity)
    }

    override fun executePreparedAddition() {
        currentEntities.addAll(additionEntities)
        additionEntities.clear()
    }

    override fun prepareEntityDeletion(entities: List<T>) {
        deletionEntities.addAll(entities)
    }

    override fun prepareEntityDeletion(entity: T) {
        deletionEntities.add(entity)
    }

    override fun executePreparedDeletion() {
        currentEntities.removeAll { deletionEntities.contains(it) }
        deletionEntities.clear()
    }

}