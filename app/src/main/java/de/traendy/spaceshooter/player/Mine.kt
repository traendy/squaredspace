package de.traendy.spaceshooter.player

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.game.GameConfig

class Mine(
    private val worldHeight: Int,
    override var xPos: Float, override var yPos: Float, private val drift: Int
) : Entity {

    private val collisionBox: RectF = RectF()
    private val mVelocity = GameConfig.mineVelocity

    private val mRadius = GameConfig.mineSize
    private var hit = false

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

    override fun updatePosition(x: Float, y: Float) {
        yPos += mVelocity
        xPos += drift
        collisionBox.set(xPos, yPos, xPos + mRadius, yPos + mRadius)
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.drawRect(collisionBox, circleFillPaint)
        canvas.drawRect(collisionBox, circleSolidPaint)
        canvas.drawPoint(xPos-10, yPos-10, circleSolidPaint)
        canvas.restore()
    }

    override fun getCollisionBox(): List<RectF> {
        return listOf(collisionBox)
    }

    override fun isAlive(): Boolean = yPos <= worldHeight + mRadius && !hit

    override fun kill() {
        hit = true
    }
}