package de.traendy.spaceshooter.engine

import android.graphics.Canvas
import android.graphics.RectF

interface Entity {
    fun updatePosition(x:Int, y:Int)
    fun draw(canvas: Canvas)
    fun getCollisionBox():RectF
    fun isAlive():Boolean
    fun kill()
}