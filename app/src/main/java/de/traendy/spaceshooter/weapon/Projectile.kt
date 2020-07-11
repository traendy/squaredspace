package de.traendy.spaceshooter.weapon

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.getAnticipateInterpolator

class Projectile(private val xPos: Int, private val yPos: Int,private val drift:Int):
    Entity {
    private var _X: Float = xPos.toFloat()
    val mX = _X
    private var _Y: Float = yPos.toFloat()
    val mY = _Y
    private var _Radius = 0
    val mRadius = _Radius
    val mWith = 10f
    val mHeight = 60f
    val mVelocity = -15
    var hit = false
    var interpolatorPosition = 0.7f

    public val mPaint = Paint().apply {
        color = Color.parseColor("#FF4411")
        strokeWidth = 10f
    }

    public val mPaintTail = Paint().apply {
        color = Color.parseColor("#22FF4444")
        strokeWidth = 10f
    }

    private var _rect: RectF = RectF()
    private var rectTail: RectF = RectF()
    public val mRectF = _rect

    override fun updatePosition(x: Int, y: Int) {
        _Y += mVelocity * getAnticipateInterpolator(interpolatorPosition, 0.0f)
        _X += drift
        if(interpolatorPosition < 1.0){
            interpolatorPosition += 0.01f
        }
        _rect.set(_X.toFloat(), _Y.toFloat(), _X + mWith, _Y + mHeight)
        rectTail.set(_X.toFloat(), _Y.toFloat()+mHeight, _X + mWith, _Y + mHeight+30)
    }

    override fun isAlive(): Boolean = _Y >= -mHeight && !hit

    override fun kill() {
        hit = true
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(mRectF, mPaint)
        canvas.drawRect(rectTail, mPaintTail)
    }

    override fun getCollisionBox(): RectF {
        return _rect
    }
}