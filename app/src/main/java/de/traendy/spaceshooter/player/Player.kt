package de.traendy.spaceshooter.player

import android.graphics.*
import de.traendy.spaceshooter.BuildConfig
import de.traendy.spaceshooter.engine.Entity

open class Player(
    private val mPaint: Paint,
    protected val rectF: RectF
) : Entity {

    override var xPos: Float = 0f
    override var yPos: Float = 0f
    val mWith = 150f
    val mHeight = 150f
    open var hitPoints = 3
    protected open var living = false

    fun setSpawn(posX: Float, posY: Float) {
        xPos = posX
        yPos = posY
        rectF.set(posX, posY, posX + mWith, posY + mHeight)
        rectF.offset(-mWith / 2, -mHeight / 2)
    }

    override fun updatePosition(x: Float, y: Float) {
        if (inBounds(x, y)) {
            xPos = x
            yPos = y
            rectF.set(xPos, yPos, xPos + mWith, yPos + mHeight)
            rectF.offset(-mWith / 2, -mHeight / 2)
        }

    }

    fun inBounds(x: Float, y: Float): Boolean {
        return x >= rectF.left && x <= rectF.right && y >= rectF.top && y <= rectF.bottom
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        if (BuildConfig.DEBUG) {
            canvas.drawRect(rectF, mPaint)
        }
        canvas.restore()
    }


    override fun getCollisionBox(): RectF {
        return rectF
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

    fun getProjectileSpawnPosition(): Pair<Int, Int> {
        return Pair(
            (rectF.left + mWith / 2).toInt(),
            rectF.top.toInt()
        )
    }

    fun revive() {
        hitPoints = 3
        living = true
        yPos = -100f
    }
}