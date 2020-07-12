package de.traendy.spaceshooter.weapon

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.getAnticipateInterpolator

class Projectile(private var xPos: Float, private var yPos: Float, private val drift: Int) :
    Entity {
    private val mWith = 10f
    private val mHeight = 60f
    private val mVelocity = -15
    private var hit = false
    private var interpolatorPosition = 0.7f

    private val mPaint = Paint().apply {
        color = Color.parseColor("#FF4411")
        strokeWidth = 10f
    }

    private val mPaintTail = Paint().apply {
        color = Color.parseColor("#22FF4444")
        strokeWidth = 10f
    }

    private var _rect: RectF = RectF()
    private var rectTail: RectF = RectF()
    private val mRectF = _rect

    override fun updatePosition(x: Float, y: Float) {
        yPos += mVelocity * getAnticipateInterpolator(interpolatorPosition, 0.0f)
        xPos += drift
        if (interpolatorPosition < 1.0) {
            interpolatorPosition += 0.01f
        }
        _rect.set(xPos, yPos, xPos + mWith, yPos + mHeight)
        rectTail.set(xPos, yPos + mHeight, xPos + mWith, yPos + mHeight + 30)
    }

    override fun isAlive(): Boolean = yPos >= -mHeight && !hit

    override fun kill() {
        hit = true
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(mRectF, mPaint)
        canvas.drawRect(rectTail, mPaintTail)
    }

    override fun getCollisionBox(): RectF {
        return _rect
    }
}