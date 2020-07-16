package de.traendy.spaceshooter.hud

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import de.traendy.spaceshooter.game.GameState
import de.traendy.spaceshooter.player.Invulnerability

class MetaHud {

    private var screenWidth:Float = 0.0f
    private val mSize = 30f
    public fun updateScreenWidth(width: Float) {
        screenWidth = width
    }
    private val hitPointsHud = HitPointsHud(50f, 20f)

    private val textPaint = TextPaint().apply {
        color = Color.parseColor("#FFFFFF")
        textSize = 40f
    }

    private val textPowerUpPaint = TextPaint().apply {
        color = Color.parseColor("#FF55AA33")
        textSize = 40f
        isAntiAlias = true
    }

    private val textPointsPaint = TextPaint().apply {
        color = Color.parseColor("#FFDDDD33")
        textSize = 40f
        isAntiAlias = true
    }

    private val textTimePaint = TextPaint().apply {
        color = Color.parseColor("#FF00DDAA")
        textSize = 40f
        isAntiAlias = true
    }

    private val collisionPaint = Paint().apply {
        color = Color.parseColor("#00000000")
        isAntiAlias = true
    }



    public fun drawHud(canvas: Canvas, hitPoints: Int, gameState:GameState, playerInvulnerability: Invulnerability) {
        canvas.drawText("P", 40f, 50f, textPointsPaint)
        canvas.drawText("${gameState.highScore()} Points", 80f, 50f, textPaint)
        canvas.drawText("S", 40f, 100f, textPowerUpPaint)
        canvas.drawText("${gameState.projectileSpawningInterval}", 80f, 100f, textPaint)
        canvas.drawText("T", 40f, 150f, textTimePaint)
        val time = (System.currentTimeMillis() - gameState.startTime) / 1000
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