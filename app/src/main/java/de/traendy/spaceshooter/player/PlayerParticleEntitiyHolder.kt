package de.traendy.spaceshooter.player

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import kotlin.random.Random

class PlayerParticleEntitiyHolder(private val mPaint:Paint, private val mBorderPaint: Paint):
    PrimitiveEntityHolder<PlayerParticle>() {




    init {
        repeat(50) {
            prepareEntityAddition(
                PlayerParticle(
                    0f, 0f,
                    Random.nextInt(10, 40).toFloat(), Random.nextInt(40, 100).toFloat(), it.rem(
                        2
                    ) == 0, Random.nextInt(1, 5),false, mPaint, mBorderPaint
                )
            )
        }
    }

    fun updateVisibility(visible:Boolean){
        getAllEntities().forEach { it.setVisibility(visible) }
    }

    fun updatePosition(x: Int, y: Int){
        getAllEntities().forEach { it.updatePosition(x, y) }

    }

    fun removeParticles(number: Int){
        if (number > 0 && getAllEntities().size > number) {
            prepareEntityDeletion(getAllEntities().subList(0, number))
        }
    }

    fun draw(canvas: Canvas){
        getAllEntities().forEach { it.draw(canvas) }
    }

    fun reset() {
        repeat(50-getAllEntities().size) {
            prepareEntityAddition(
                PlayerParticle(
                    0f, 0f,
                    Random.nextInt(10, 40).toFloat(), Random.nextInt(40, 100).toFloat(), it.rem(
                        2
                    ) == 0, Random.nextInt(1, 5), false, mPaint, mBorderPaint
                )
            )
        }
    }
}