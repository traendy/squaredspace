package de.traendy.spaceshooter.effects

import android.graphics.*
import de.traendy.spaceshooter.engine.*
import kotlin.random.Random

class Exhaust(override var xPos: Float = 0f, override var yPos: Float = 0f) :Entity {

    private val speed = 2f
    private val drift = 0.3f
    private var interpolator = 0.0f
    private var showing = true
    private val maxSize = 50f
    private val maxAlpha = 255 //(255 / 16) * 10
    private val textColorARGB = ColorARGB(maxAlpha, 0, (255 / 16) * 10, (255 / 16) * 3)
    private val rectF = RectF()
    private val mPlayerPaint = Paint().apply {
        color = Color.parseColor("#5500AA33")
        strokeWidth = 10f
    }
    private val paint = Paint().apply {
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
        xPos += drift * if(Random.nextBoolean()) 1 else -1
        yPos += speed
        val size = (maxSize * getAnticipateOvershootInterpolator(interpolator, 1f))
        rectF.set(xPos-size/2, yPos, xPos+size/2, yPos+size)
    }

    override fun draw(canvas: Canvas) {
        if (showing) {
            val alpha = (maxAlpha * (1 - getAccelerateDecelerateInterpolator(interpolator))).toInt()
            paint.color =
                Color.argb(alpha, textColorARGB.red, textColorARGB.green, textColorARGB.blue)
            canvas.drawRect(rectF, paint)
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