package de.traendy.spaceshooter.powerup

import android.graphics.*
import android.text.TextPaint
import de.traendy.spaceshooter.engine.Entity




class PowerUp(private val worldHeight: Int, private val xPos: Int):
    Entity {


    private val circleSolidRect = RectF()
    private val circleShadowRect = RectF()
    private val mVelocity = 10f
    private val mSize = 60f
    private var _Y = -mSize
    private var _X = xPos.toFloat()
    private var consumed = false
    private var scale = 0f
    private var directionScale = 0

    private val textPaint = TextPaint().apply {
        color = Color.parseColor("#FF55AA33")
        textSize = mSize
        isAntiAlias = true
    }
    private val textShadow = TextPaint().apply {
        color = Color.parseColor("#99338811")
        textSize = mSize
        isAntiAlias = true
    }
    private val collisionPaint = Paint().apply {
        color = Color.parseColor("#00000000")
        isAntiAlias = true
    }

    private val circleSolidPaint = Paint().apply {
        color = Color.parseColor("#FF55AA33")
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val circleShadowdPaint = Paint().apply {
        color = Color.parseColor("#99338811")
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    override fun updatePosition(x: Int, y: Int) {
        _Y += mVelocity
        circleSolidRect.set(_X -mSize / 2 + 5, _Y - mSize -5, _X + mSize +5, _Y + mSize / 2 -5)
        circleShadowRect.set(_X -mSize / 2 + 10, _Y - mSize, _X + mSize +10, _Y + mSize / 2)
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
//        canvas.translate(canvas.width / 2f, canvas.height / 2f);
//        val scaleFactor = scale(0.01f)
//        canvas.scale(scaleFactor, scaleFactor)
//        canvas.translate(-canvas.width / 2f, -canvas.height / 2f);
        canvas.drawRect(circleSolidRect, collisionPaint)
        canvas.drawOval(circleShadowRect, circleShadowdPaint)
        canvas.drawOval(circleSolidRect, circleSolidPaint)
        canvas.drawText("S", _X+5f, _Y+5f, textShadow)
        canvas.drawText("S", _X, _Y, textPaint)
        canvas.restore()
    }

    private fun scale(delta: Float): Float {
        scale += delta * directionScale
        if (scale <= 0.5f) {
            directionScale = 1
            scale = 0.5f
        } else if (scale >= 1) {
            directionScale = -1
            scale = 1f
        }
        return scale
    }


    override fun getCollisionBox(): RectF {
        return circleSolidRect
    }

    override fun isAlive(): Boolean
        = _Y <= worldHeight && !consumed


    override fun kill() {
        consumed = true
    }
}