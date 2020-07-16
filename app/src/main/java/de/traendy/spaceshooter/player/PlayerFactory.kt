package de.traendy.spaceshooter.player

import android.graphics.*
import de.traendy.spaceshooter.effects.Lightning
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

    fun create(bitmap: Bitmap?):Player{
        return Player(
            Paint().apply {
                color = Color.parseColor("#FFFFFFFF")
                strokeWidth = 10f
                style = Paint.Style.STROKE
            }, RectF(),
            bitmap
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