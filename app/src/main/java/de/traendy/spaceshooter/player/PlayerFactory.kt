package de.traendy.spaceshooter.player

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.engine.EntityFactory

object PlayerFactory: EntityFactory<Player> {
    override fun create(): Player {
        return Player(
            Paint().apply {
                color = Color.parseColor("#20FFFFFF")
                strokeWidth = 10f
                style = Paint.Style.STROKE
            }, RectF()
        )
    }

    fun createBoss():Boss {
        return Boss(
            Paint().apply
            {
                color = Color.parseColor("#20FFFFFF")
                strokeWidth = 10f
                style = Paint.Style.STROKE
            }, RectF()
        )
    }
}