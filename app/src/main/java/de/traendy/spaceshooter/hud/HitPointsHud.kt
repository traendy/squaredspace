package de.traendy.spaceshooter.hud

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class HitPointsHud(
    private val size: Float,
    private val distance: Float
) {

    private var screenWidth: Float = 0f
    private var numberOfHitpoints = 0
    private val mPlayerPaint = Paint().apply {
        color = Color.parseColor("#5500AA33")
        strokeWidth = 10f
    }
    private val mPlayerBorderPaint = Paint().apply {
        color = Color.parseColor("#CC00AA33")
        strokeWidth = 5f
        style = Paint.Style.STROKE
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

    public fun updateHitPoints(hitpoints: Int) {
        numberOfHitpoints = hitpoints
    }

    fun draw(canvas: Canvas) {
        for (i in 1..numberOfHitpoints) {
            canvas.drawRect(mRectF, mPlayerPaint)
            canvas.drawRect(mRectF, mPlayerBorderPaint)
            mRectF.offset(-(size + distance), 0f)
        }

    }

}