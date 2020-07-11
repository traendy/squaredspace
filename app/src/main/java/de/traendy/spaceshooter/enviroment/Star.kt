package de.traendy.spaceshooter.enviroment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.engine.Entity

import kotlin.random.Random

class Star(private val worldHeight: Int, private val xPos: Int) :
    Entity {
    private var radius = 1f + Random.nextInt(20)
    private var _Y: Int = 0
    var fillRectF = RectF()
    val colors = arrayOf(
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
    public val fillPaint = Paint().apply {
        color = colors[Random.nextInt(colors.size)]

        strokeWidth = 10f
    }

    override fun updatePosition(x: Int, y: Int) {
        _Y += radius.toInt()
        fillRectF.set(xPos.toFloat(), _Y.toFloat(), xPos + radius, _Y + radius)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(fillRectF,fillPaint)
    }

    override fun getCollisionBox(): RectF {
        return fillRectF
    }

    override fun isAlive(): Boolean = _Y <= worldHeight

    override fun kill() {

    }
}