package de.traendy.spaceshooter.engine

import android.graphics.Canvas
import android.graphics.RectF

interface Entity {
    fun updatePosition(x:Float, y:Float)
    fun draw(canvas: Canvas)
    fun getCollisionBox():RectF
    fun isAlive():Boolean
    fun kill()
}