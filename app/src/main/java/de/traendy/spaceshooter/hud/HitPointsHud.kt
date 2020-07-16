package de.traendy.spaceshooter.hud

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.player.Invulnerability

class HitPointsHud(
    private val size: Float,
    private val distance: Float
) {

    private var screenWidth: Float = 0f
    private var numberOfHitPoints = 0
    private var indicateVulnerability = false;
    private val mPlayerPaint = Paint().apply {
        color = Color.parseColor("#5500AA33")
        strokeWidth = 10f
    }
    private val mPlayerBorderPaint = Paint().apply {
        color = Color.parseColor("#CC00AA33")
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private val mShieldPaint = Paint().apply {
        color = Color.parseColor("#CCCCCCCC")
        strokeWidth = 10f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    public fun updateScreenWidth(width: Float) {
        screenWidth = width
        mRectF.left = screenWidth - size - distance
        mRectF.right = screenWidth - distance
    }

    private val mRectF = RectF().apply {
        set(
            screenWidth - size - distance,
            distance,
            screenWidth - distance,
            distance + size
        )
    }

    public fun updateHitPoints(hitPoints: Int, playerInvulnerability: Invulnerability) {
        numberOfHitPoints = hitPoints
        indicateVulnerability = playerInvulnerability.isVulnerable(System.currentTimeMillis())

    }

    fun draw(canvas: Canvas) {
        for (i in 1..numberOfHitPoints) {
            canvas.drawRect(mRectF, mPlayerPaint)
            if (indicateVulnerability) {
                canvas.drawRect(mRectF, mPlayerBorderPaint)
            } else {
                canvas.drawRect(mRectF, mShieldPaint)
            }
            mRectF.offset(-(size + distance), 0f)
        }

    }

}