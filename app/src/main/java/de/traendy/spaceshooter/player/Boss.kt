package de.traendy.spaceshooter.player

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.BuildConfig
import de.traendy.spaceshooter.engine.Entity

class Boss(private val mPaint: Paint, private val rectF: RectF) : Entity {

    private var relativePosition = 0F
    override var xPos: Float = 0f
    override var yPos: Float = 0f
    private val mWith = 150f
    private val mHeight = 150f
    private var hitPoints = 3
    private var living = false

    override fun updatePosition(x: Float, y: Float) {
        xPos = updateRelativePosition(x)
        if (yPos < 100) {
            yPos += 10
        }
        rectF.set(xPos, yPos, xPos + mWith, yPos + mHeight)
        rectF.offset(-mWith / 2, -mHeight / 2)
    }

    private fun updateRelativePosition(playerPosition: Float): Float {
        relativePosition += 0.1F
        return (playerPosition + kotlin.math.sin(
            relativePosition.rem(360)
        ) * 100)
    }

    fun getMineSpawnPosition(): Pair<Int, Int> {
        return Pair(
            (rectF.left + mWith / 2).toInt(),
            rectF.bottom.toInt()
        )
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        if (BuildConfig.DEBUG) {
            canvas.drawRect(rectF, mPaint)
        }
        canvas.restore()
    }


    override fun getCollisionBox(): List<RectF> {
        return listOf(rectF)
    }

    override fun isAlive(): Boolean {
        return living
    }

    override fun kill() {
        hitPoints--
        if (hitPoints <= 0) {
            living = false
        }
    }


    fun revive() {
        hitPoints = 3
        living = true
        yPos = -100f
    }
}