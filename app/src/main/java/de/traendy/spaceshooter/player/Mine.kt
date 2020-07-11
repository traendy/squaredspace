package de.traendy.spaceshooter.player

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.getAccelerateDecelerateInterpolator
import de.traendy.spaceshooter.engine.getAnticipateInterpolator
import kotlin.random.Random

class Mine(private val worldHeight: Int, private val xPos: Int, private val yPos: Int, private val drift: Int):Entity {

    private val collisionBox: RectF = RectF()
    val mVelocity = 10
    private var _X: Float = xPos.toFloat()
    val mX = _X
    private var _Y: Float = yPos.toFloat()
    val mY = _Y
    val mRadius = 60f
    var hit = false

    private val circleSolidPaint = Paint().apply {
        color = Color.parseColor("#FFFF2233")
        style = Paint.Style.STROKE
        strokeWidth = 10f
        isAntiAlias = true
    }
    private val circleFillPaint = Paint().apply {
        color = Color.parseColor("#77FF2233")
        style = Paint.Style.FILL
    }

    override fun updatePosition(x: Int, y: Int) {
        _Y += mVelocity
        _X += drift
        collisionBox.set(_X.toFloat(), _Y.toFloat(), _X + mRadius, _Y + mRadius)
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.drawOval(collisionBox, circleFillPaint)
        canvas.drawOval(collisionBox, circleSolidPaint)
        canvas.drawPoint(_X.toFloat(), _Y.toFloat(), circleSolidPaint)
        canvas.restore()
    }

    override fun getCollisionBox(): RectF {
        return collisionBox
    }

    override fun isAlive(): Boolean = _Y <= worldHeight + mRadius && !hit

    override fun kill() {
       hit = true
    }
}