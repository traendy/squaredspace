package de.traendy.spaceshooter.obstacle

import android.graphics.*
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.getAccelerateDecelerateInterpolator
import de.traendy.spaceshooter.engine.getAnticipateInterpolator
import de.traendy.spaceshooter.engine.getDecelerateInterpolation
import de.traendy.spaceshooter.game.GameConfig
import kotlin.random.Random

class Meteor(private val worldHeight: Int, private val xPos: Int) :
    Entity {
    private var _X: Float = xPos.toFloat()
    private var _Y: Float = -150f
    private var radius = GameConfig.meteorBaseSize + Random.nextInt(100)
    var mVelocity = GameConfig.meteorBaseVelocity + Random.nextInt(12)
    var destructionAngle = 0
    var shot = false

    private var angle = Random.nextInt(720)
    val rotationSpeed = Random.nextInt(1, 3)
    private var direction = if (Random.nextBoolean()) 1 else -1

    var interpolationPosition = 0.0f

    public var fillPaint = Paint().apply {
        color = Color.parseColor(GameConfig.meteorFillColor)
        strokeWidth = 10f
        isAntiAlias = true
    }
    public val borderPaint = Paint().apply {
        color = Color.parseColor(GameConfig.meteorBorderColor)
        strokeWidth = 10f
        style = Paint.Style.STROKE
        isDither
    }

    var fillRect: RectF = RectF()
    private var borderRect: RectF = RectF()


    override fun updatePosition(x: Int, y: Int) {

        _Y += mVelocity * getDecelerateInterpolation(interpolationPosition, 1.0f)
        _X += destructionAngle * getDecelerateInterpolation(interpolationPosition, 1.0f)
        if (interpolationPosition < 1.0) {
            interpolationPosition += 0.01f
        }

        fillRect.set(_X.toFloat(), _Y.toFloat(), _X + radius, _Y + radius)
        borderRect.set(_X, _Y, _X + radius, (_Y + radius))
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
            _X.toFloat() + radius / 2,
            _Y.toFloat() + radius / 2
        )
        canvas.drawRect(fillRect, fillPaint)
        canvas.drawRect(borderRect, borderPaint)
        canvas.restore()
    }

    override fun getCollisionBox(): RectF {
        return fillRect
    }

    override fun isAlive(): Boolean = _Y <= worldHeight + radius && !shot

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

    fun clone(): Meteor {
        val meteor = Meteor(worldHeight, xPos)
        radius /= 2
        meteor.radius = radius
        destructionAngle = Random.nextInt(1, 2)
        meteor.destructionAngle = Random.nextInt(-3, -1)
        meteor.mVelocity = mVelocity + Random.nextInt(-3, 1)
        mVelocity += Random.nextInt(-3, 1)

        interpolationPosition = 0.0f
        meteor._Y = _Y
        meteor.angle = angle
        meteor.direction = direction * -1

        return meteor
    }
}