package de.traendy.spaceshooter.player

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.getAnticipateInterpolator
import de.traendy.spaceshooter.engine.getDecelerateInterpolation
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class PlayerParticle(var xPos:Float, var yPos: Float, var particleSize:Float, var rotationRadius:Float, val clockWise:Boolean, val speed:Int, var visible:Boolean = false,
                     private val mPaint:Paint, private val mBorderPaint:Paint):
    Entity {

    private var angle = Random.nextInt(720)
    private val rectF = RectF()

    private val borderRectF = RectF()
    private var direction = -1
    init {
        if(clockWise) direction = 1
    }

    override fun updatePosition(x: Int, y: Int) {

        xPos = x.toFloat()
        yPos = y.toFloat()

        val tRadius = rotationRadius - particleSize / 2
        val orbitRectX =
            xPos + tRadius * direction * sin(Math.PI * angle / 360).toFloat() - particleSize / 2

        val orbitRectY =
            yPos + tRadius * direction * cos(Math.PI * angle / 360).toFloat() - particleSize / 2

        rectF.set(
            orbitRectX,
            orbitRectY,
            orbitRectX + particleSize,
            orbitRectY + particleSize
        )
        borderRectF.set(
            orbitRectX,
            orbitRectY,
            orbitRectX + particleSize,
            orbitRectY + particleSize
        )
        if (angle < 720) {
            angle += 2*speed
        } else {
            angle = 0
        }
    }

    override fun draw(canvas: Canvas) {
        if(visible) {
            canvas.save()
            canvas.rotate(direction * angle.toFloat(), xPos, yPos)
            canvas.drawRect(rectF, mPaint)
            canvas.drawRect(borderRectF, mBorderPaint)
            canvas.restore()
        }
    }

    fun setVisibility(visible: Boolean){
        this.visible = visible
    }

    override fun getCollisionBox(): RectF {
        return rectF
    }

    override fun isAlive(): Boolean {
        return true
    }

    override fun kill() {
        // nothing
    }

}