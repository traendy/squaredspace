package de.traendy.spaceshooter.effects

import android.graphics.Canvas
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder

class PointsEntityHolder : PrimitiveEntityHolder<Points>() {

    fun spawnPoints(posX:Float, posY:Float, points: Int){
        prepareEntityAddition(
            Points(posX , posY, points)
        )
    }

    fun draw(canvas: Canvas){
        getAllEntities().forEach {
            it.draw(canvas) }
    }

    fun clean(){
        prepareEntityDeletion(getAllEntities().filter { !it.isAlive() })
    }
}