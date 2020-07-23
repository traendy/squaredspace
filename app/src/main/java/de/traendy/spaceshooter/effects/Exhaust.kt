package de.traendy.spaceshooter.effects

import android.graphics.*
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.getDecelerateInterpolation

class Exhaust(override var xPos: Float = 0f, override var yPos: Float = 0f) :Entity {

    private var interpolator = 0.0f
    private var showing = true
    private val size = 50f
    private val textColorARGB = ColorARGB(255, 255, 255, 0)
    private val paint = Paint().apply {
        textSize = size
        color = Color.argb(
            textColorARGB.alpha,
            textColorARGB.red,
            textColorARGB.green,
            textColorARGB.blue
        )
        isAntiAlias = true
        typeface = Typeface.DEFAULT_BOLD
    }

    override fun updatePosition(x: Float, y: Float) {
        // unused
    }

    override fun draw(canvas: Canvas) {
        if (showing) {
            val alpha = (255 * (1 - getDecelerateInterpolation(interpolator, 1f))).toInt()
            paint.color =
                Color.argb(alpha, textColorARGB.red, textColorARGB.green, textColorARGB.blue)

            interpolator += 0.01f
        }
        if (interpolator >= 1.0) {
            showing = false
        }

    }

    override fun getCollisionBox(): List<RectF> {
        return emptyList()
    }

    override fun isAlive(): Boolean {
        return showing
    }

    override fun kill() {
        showing = false
    }
}