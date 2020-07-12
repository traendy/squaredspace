package de.traendy.spaceshooter.engine

import org.junit.Test

import org.junit.Assert.*

class PrimitiveEntityHolderTest {

    private val primitiveEntityHolder = PrimitiveEntityHolder<Entity>()

    @Test
    fun getAllEntities() {
        assertEquals(0, primitiveEntityHolder.getAllEntities().size)
        primitiveEntityHolder.prepareEntityAddition(TestEntityFactory.create())
        assertEquals(0, primitiveEntityHolder.getAllEntities().size)
        primitiveEntityHolder.executePreparedAddition()
        assertEquals(1, primitiveEntityHolder.getAllEntities().size)
    }

    @Test
    fun prepareEntityAddition() {
        assertEquals(
            0,
            primitiveEntityHolder.getAllEntities().size
        )
        primitiveEntityHolder.prepareEntityAddition(TestEntityFactory.create())
        assertEquals(0, primitiveEntityHolder.getAllEntities().size)
        primitiveEntityHolder.executePreparedAddition()
        assertEquals(
            1,
            primitiveEntityHolder.getAllEntities().size
        )
    }

    @Test
    fun testPrepareEntityAddition() {
        assertEquals(0, primitiveEntityHolder.getAllEntities().size)
        val list = mutableListOf<Entity>()
        list.add(TestEntityFactory.create())
        list.add(TestEntityFactory.create())
        primitiveEntityHolder.prepareEntityAddition(list)
        assertEquals(
            0,
            primitiveEntityHolder.getAllEntities().size
        )
        primitiveEntityHolder.executePreparedAddition()
        assertEquals(2, primitiveEntityHolder.getAllEntities().size)
    }

    @Test
    fun executePreparedAddition() {
        assertEquals(0, primitiveEntityHolder.getAllEntities().size)
        primitiveEntityHolder.prepareEntityAddition(TestEntityFactory.create())
        assertEquals(0, primitiveEntityHolder.getAllEntities().size)
        primitiveEntityHolder.executePreparedAddition()
        assertEquals(
            1,
            primitiveEntityHolder.getAllEntities().size
        )
    }

    @Test
    fun prepareEntityDeletion() {
        assertEquals(
            0,
            primitiveEntityHolder.getAllEntities().size
        )
        primitiveEntityHolder.prepareEntityDeletion(TestEntityFactory.create())
        assertEquals(
            0,
            primitiveEntityHolder.getAllEntities().size
        )
        val entity = TestEntityFactory.create()
        primitiveEntityHolder.prepareEntityAddition(entity)
        primitiveEntityHolder.executePreparedAddition()
        assertEquals(
            1,
            primitiveEntityHolder.getAllEntities().size
        )
        primitiveEntityHolder.prepareEntityDeletion(entity)
        assertEquals(1, primitiveEntityHolder.getAllEntities().size)
        primitiveEntityHolder.executePreparedDeletion()
        assertEquals(0, primitiveEntityHolder.getAllEntities().size)
    }

    @Test
    fun testPrepareEntityDeletion() {
        assertEquals(
            0,
            primitiveEntityHolder.getAllEntities().size
        )
        primitiveEntityHolder.prepareEntityDeletion(TestEntityFactory.create())
        assertEquals(
            0,
            primitiveEntityHolder.getAllEntities().size
        )
        val entity = TestEntityFactory.create()
        val entity2 = TestEntityFactory.create()
        val list = mutableListOf<Entity>()
        list.add(entity2)
        primitiveEntityHolder.prepareEntityAddition(entity)
        primitiveEntityHolder.prepareEntityAddition(entity2)
        primitiveEntityHolder.executePreparedAddition()
        assertEquals(
            2,
            primitiveEntityHolder.getAllEntities().size
        )
        primitiveEntityHolder.prepareEntityDeletion(list)
        assertEquals(2, primitiveEntityHolder.getAllEntities().size)
        primitiveEntityHolder.executePreparedDeletion()
        assertEquals(1, primitiveEntityHolder.getAllEntities().size)
    }

    @Test
    fun executePreparedDeletion() {
        assertEquals(
            0,
            primitiveEntityHolder.getAllEntities().size
        )
        primitiveEntityHolder.prepareEntityDeletion(TestEntityFactory.create())
        assertEquals(0, primitiveEntityHolder.getAllEntities().size)
        val entity = TestEntityFactory.create()
        primitiveEntityHolder.prepareEntityAddition(entity)
        primitiveEntityHolder.executePreparedAddition()
        assertEquals(1, primitiveEntityHolder.getAllEntities().size)
        primitiveEntityHolder.prepareEntityDeletion(entity)
        assertEquals(1, primitiveEntityHolder.getAllEntities().size)
        primitiveEntityHolder.executePreparedDeletion()
        assertEquals(0, primitiveEntityHolder.getAllEntities().size)
    }
}