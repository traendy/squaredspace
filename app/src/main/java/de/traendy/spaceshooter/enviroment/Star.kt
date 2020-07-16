package de.traendy.spaceshooter.enviroment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.engine.Entity

import kotlin.random.Random

class Star(private val worldHeight: Float, override val xPos: Float) :
    Entity {
    private var radius = 1f + Random.nextInt(20)
    override var yPos: Float = 0f
    private var fillRectF = RectF()
    private val starColors = arrayOf(
        Color.parseColor("#DDFFFFFF"),
        Color.parseColor("#44AAFFFF"),
        Color.parseColor("#33FFAAFF"),
        Color.parseColor("#99FFFFAA"),
        Color.parseColor("#DDFFAAAA"),
        Color.parseColor("#FFAAAAFF"),
        Color.parseColor("#CCAAFFAA"),
        Color.parseColor("#AAADFAEF"),
        Color.parseColor("#77ADFAEF"),
        Color.parseColor("#66ADFAEF"),
        Color.parseColor("#DD44FFFF"),
        Color.parseColor("#55FF33FF"),
        Color.parseColor("#fFFFFF33"),
        Color.parseColor("#AA12FF44")
    )
    private val fillPaint = Paint().apply {
        color = starColors[Random.nextInt(starColors.size)]
        strokeWidth = 10f
    }

    override fun updatePosition(x: Float, y: Float) {
        yPos += radius
        fillRectF.set(xPos, yPos, xPos + radius, yPos + radius)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(fillRectF, fillPaint)
    }

    override fun getCollisionBox(): List<RectF> = listOf(fillRectF)

    override fun isAlive(): Boolean = yPos <= worldHeight

    override fun kill() {
        // unused
    }
}