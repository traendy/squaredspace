package de.traendy.spaceshooter.player

import android.graphics.*
import de.traendy.spaceshooter.BuildConfig
import de.traendy.spaceshooter.engine.Entity

open class Player(
    private val mPaint: Paint,
    protected val rectF: RectF,
    private val bitmap: Bitmap? = null
) : Entity {

    override var xPos: Float = 0f
    override var yPos: Float = 0f
    val mWith = 100f
    val mHeight = 250f
    open var hitPoints = 3
    private val mWidth2 = 250f
    private val mHeight2 = 70f
    protected open var living = false
    private val rectF2 = RectF()
    private val rectF3 = RectF()

    fun setSpawn(posX: Float, posY: Float) {
        setPositionBox(posX, posY)
    }

    override fun updatePosition(x: Float, y: Float) {
        if (inBounds(x, y)) {
            setPositionBox(x, y)
        }
    }

    private fun setPositionBox(x: Float, y: Float) {
        xPos = x
        yPos = y
        rectF.set(xPos, yPos, xPos + mWith, yPos + mHeight)
        rectF2.set(xPos, yPos, xPos + mWidth2, yPos + mHeight2)
        rectF3.set(xPos, yPos, xPos + mWidth2, yPos + mHeight)
        rectF.offset(-mWith / 2, -mHeight / 2)
        rectF2.offset(-mWidth2 / 2, +10f)
        rectF3.offset(-mWidth2 / 2, -mHeight / 2)
    }

    fun inBounds(x: Float, y: Float): Boolean {
        return x >= rectF3.left && x <= rectF3.right && y >= rectF3.top && y <= rectF3.bottom
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        if (BuildConfig.DEBUG) {
            canvas.drawRect(rectF, mPaint)
            canvas.drawRect(rectF2, mPaint)
        }
        bitmap?.let{
            canvas.drawBitmap(bitmap,null, rectF3, mPaint)

        }
        canvas.restore()
    }


    override fun getCollisionBox(): List<RectF> {
        return listOf(rectF, rectF2)
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