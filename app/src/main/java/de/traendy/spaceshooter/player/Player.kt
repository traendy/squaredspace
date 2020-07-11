package de.traendy.spaceshooter.player

import android.graphics.*
import de.traendy.spaceshooter.engine.Entity

open class Player(
    private val mPaint:Paint, protected val rectF: RectF
) : Entity {

    var _X: Int = 0
    var _Y: Int = 0
    val mWith = 150f
    val mHeight = 150f
    open var hitPoints = 3
        get() = field
    protected open var living = true

    fun setSpawn(posX: Int, posY: Int) {
        _X = posX
        _Y = posY
        rectF.set(posX.toFloat(), posY.toFloat(), posX + mWith, posY + mHeight)
        rectF.offset(-mWith / 2, -mHeight / 2)
    }

    override fun updatePosition(x: Int, y: Int) {
        if (inBounds(x, y)) {
            _X = x
            _Y = y
            rectF.set(x.toFloat(), y.toFloat(), x + mWith, y + mHeight)
            rectF.offset(-mWith / 2, -mHeight / 2)
        }

    }

    fun inBounds(x: Int, y: Int): Boolean {
        return x >= rectF.left && x <= rectF.right && y >= rectF.top && y <= rectF.bottom
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
//        canvas.drawRect(rectF, mPaint)
        canvas.restore()
    }


    override fun getCollisionBox(): RectF {
        return rectF
    }

    override fun isAlive(): Boolean {
        return living
    }

    override fun kill() {
        living = false
    }

    fun getProjectileSpawnPosition():Pair<Int, Int>{
        return Pair(
            (rectF.left + mWith / 2).toInt(),
            rectF.top.toInt())
    }
}