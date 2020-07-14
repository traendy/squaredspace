package de.traendy.spaceshooter.obstacle

import android.graphics.*
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.getDecelerateInterpolation
import de.traendy.spaceshooter.game.GameConfig
import kotlin.random.Random

class Meteor(private val worldHeight: Int, override var xPos: Float) :
    Entity {
    override var yPos: Float = -150f
    private var radius = GameConfig.meteorBaseSize + Random.nextInt(100)
    private var mVelocity = GameConfig.meteorBaseVelocity + Random.nextInt(12)
    private var destructionAngle = 0
    private var shot = false

    private var angle = Random.nextInt(720)
    private val rotationSpeed = Random.nextInt(1, 3)
    private var direction = if (Random.nextBoolean()) 1 else -1

    private var interpolationPosition = 0.0f

    private var fillPaint = Paint().apply {
        color = Color.parseColor(GameConfig.meteorFillColor)
        strokeWidth = 10f
        isAntiAlias = true
    }
    private val borderPaint = Paint().apply {
        color = Color.parseColor(GameConfig.meteorBorderColor)
        strokeWidth = 10f
        style = Paint.Style.STROKE
        isDither
    }

    private var fillRect: RectF = RectF()
    private var borderRect: RectF = RectF()


    override fun updatePosition(x: Float, y: Float) {

        yPos += mVelocity * getDecelerateInterpolation(interpolationPosition, 1.0f)
        xPos += destructionAngle * getDecelerateInterpolation(interpolationPosition, 1.0f)
        if (interpolationPosition < 1.0) {
            interpolationPosition += 0.01f
        }

        fillRect.set(xPos, yPos, xPos + radius, yPos + radius)
        borderRect.set(xPos, yPos, xPos + radius, (yPos + radius))
        if (angle < 720) {
            angle += 2 * rotationSpeed
        } else {
            angle = 0
        }
    }

    override fun draw(canvas: Canvas) {
        canvas.save()
        canvas.rotate(
            direction * angle.toFloat(),
            xPos + radius / 2,
            yPos + radius / 2
        )
        canvas.drawRect(fillRect, fillPaint)
        canvas.drawRect(borderRect, borderPaint)
        canvas.restore()
    }

    override fun getCollisionBox(): RectF {
        return fillRect
    }

    override fun isAlive(): Boolean = yPos <= worldHeight + radius && !shot

    override fun kill() {
        shot = true
    }

    fun isShot(): Meteor? {
        if (radius > GameConfig.meteorMinimalSplitRadius) {
            return clone()
        } else {
            shot = true
        }
        return null
    }

    private fun clone(): Meteor {
        val meteor = Meteor(worldHeight, xPos)
        radius /= 2
        meteor.radius = radius
        destructionAngle = Random.nextInt(1, 2)
        meteor.destructionAngle = Random.nextInt(-3, -1)
        meteor.mVelocity = mVelocity + Random.nextInt(-3, 1)
        mVelocity += Random.nextInt(-3, 1)

        interpolationPosition = 0.0f
        meteor.yPos = yPos
        meteor.angle = angle
        meteor.direction = direction * -1

        return meteor
    }
}