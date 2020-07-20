package de.traendy.spaceshooter.hud

import android.graphics.Canvas
import android.graphics.Color
import android.text.TextPaint
import de.traendy.spaceshooter.game.OldGameState
import de.traendy.spaceshooter.player.Invulnerability

class MetaHud {

    private var screenWidth:Float = 0.0f
    private val mSize = 40f
    public fun updateScreenWidth(width: Float) {
        screenWidth = width
    }
    private val hitPointsHud = HitPointsHud(50f, 20f)

    private val textPaint = TextPaint().apply {
        color = Color.parseColor("#FFFFFF")
        textSize = mSize
    }

    private val textPowerUpPaint = TextPaint().apply {
        color = Color.parseColor("#FF55AA33")
        textSize = mSize
        isAntiAlias = true
    }

    private val textPointsPaint = TextPaint().apply {
        color = Color.parseColor("#FFDDDD33")
        textSize = mSize
        isAntiAlias = true
    }

    private val textTimePaint = TextPaint().apply {
        color = Color.parseColor("#FF00DDAA")
        textSize = mSize
        isAntiAlias = true
    }

    public fun drawHud(canvas: Canvas, hitPoints: Int, oldGameState:OldGameState, playerInvulnerability: Invulnerability) {
        canvas.drawText("P", 40f, 50f, textPointsPaint)
        canvas.drawText("${oldGameState.highScore()}", 80f, 50f, textPaint)
        canvas.drawText("S", 40f, 100f, textPowerUpPaint)
        canvas.drawText("${oldGameState.projectileSpawningInterval}", 80f, 100f, textPaint)
        canvas.drawText("T", 40f, 150f, textTimePaint)
        val time = (System.currentTimeMillis() - oldGameState.startTime) / 1000
        canvas.drawText("$time s", 80f, 150f, textPaint)
        drawHitPoints(canvas, hitPoints, playerInvulnerability)
    }

    private fun drawHitPoints(
        canvas: Canvas,
        hitPoints: Int,
        playerInvulnerability: Invulnerability
    ) {
        hitPointsHud.updateScreenWidth(screenWidth)
        hitPointsHud.updateHitPoints(hitPoints, playerInvulnerability)
        hitPointsHud.draw(canvas)
    }
}