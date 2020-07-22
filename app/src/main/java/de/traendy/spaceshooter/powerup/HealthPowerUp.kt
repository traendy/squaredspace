package de.traendy.spaceshooter.powerup

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.game.GameConfig

class HealthPowerUp(private val worldHeight: Float, override var xPos: Float) :
    Entity, PowerUp {


    private val circleSolidRect = RectF()
    private val circleShadowRect = RectF()
    override var mVelocity = GameConfig.powerUpSpeed
    private val mSize = GameConfig.powerUpSize
    override var yPos = -mSize
    private var consumed = false

    private val textPaint = TextPaint().apply {
        color = Color.parseColor("#FFAA3355")
        textSize = mSize
        isAntiAlias = true
    }
    private val textShadow = TextPaint().apply {
        color = Color.parseColor("#99881133")
        textSize = mSize
        isAntiAlias = true
    }
    private val collisionPaint = Paint().apply {
        color = Color.parseColor("#00000000")
        isAntiAlias = true
    }

    private val circleSolidPaint = Paint().apply {
        color = Color.parseColor("#FFAA3355")
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    private val circleShadowedPaint = Paint().apply {
        color = Color.parseColor("#99881133")
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
    }

    override fun updatePosition(x: Float, y: Float) {
        yPos += mVelocity
        circleSolidRect.set(
            xPos - mSize / 2 + 5, yPos - mSize - 5,
            xPos + mSize + 5, yPos + mSize / 2 - 5
        )
        circleShadowRect.set(
            xPos - mSize / 2 + 10,
            yPos - mSize,
            xPos + mSize + 10,
            yPos + mSize / 2
        )
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.drawRect(circleSolidRect, collisionPaint)
        canvas.drawOval(circleShadowRect, circleShadowedPaint)
        canvas.drawOval(circleSolidRect, circleSolidPaint)
        canvas.drawText("H", xPos + 5f, yPos + 5f, textShadow)
        canvas.drawText("H", xPos, yPos, textPaint)
        canvas.restore()
    }

    override fun getCollisionBox(): List<RectF> {
        return listOf(circleSolidRect)
    }

    override fun isAlive(): Boolean = yPos <= worldHeight && !consumed


    override fun kill() {
        consumed = true
    }
}