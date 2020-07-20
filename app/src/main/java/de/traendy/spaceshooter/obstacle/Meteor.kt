package de.traendy.spaceshooter.obstacle

import android.graphics.*
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.getCircleInterpolator
import de.traendy.spaceshooter.engine.getDecelerateInterpolation
import de.traendy.spaceshooter.game.GameConfig
import kotlin.random.Random

class Meteor(private val worldHeight: Int, private val worldWidth: Int, override var xPos: Float) :
    Entity {
    override var yPos: Float = -150f
    private var radius = GameConfig.meteorBaseSize + 150 * (getCircleInterpolator(Random.nextFloat(),0.5f))//Random.nextInt(150)
    private var mVelocity = GameConfig.meteorBaseVelocity + Random.nextInt(12)
    private var destructionAngle = 0f
    private var shot = false
    var damage = radius.toInt()
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

        yPos += mVelocity
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

    override fun getCollisionBox(): List<RectF> {
        return listOf(fillRect)
    }

    override fun isAlive(): Boolean = yPos <= worldHeight + radius && !shot && xPos >= 0-radius && xPos <= worldWidth + radius

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
        val meteor = Meteor(worldHeight, worldWidth, xPos)
        radius /= 1.5f
        meteor.radius = radius
        destructionAngle = 3 * getCircleInterpolator(Random.nextFloat(),1f)//Random.nextInt(1, 2)
        meteor.destructionAngle = 3 * getCircleInterpolator(Random.nextFloat(), 1f)
        meteor.mVelocity = mVelocity + Random.nextInt(-3, 1)
        mVelocity += Random.nextInt(-3, 1)

        interpolationPosition = 0.0f
        meteor.yPos = yPos
        meteor.angle = angle
        meteor.direction = direction * -1

        return meteor
    }
}