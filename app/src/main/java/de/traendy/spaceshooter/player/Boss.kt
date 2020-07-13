package de.traendy.spaceshooter.player

import android.graphics.Paint
import android.graphics.RectF

class Boss(mPaint: Paint, rectF: RectF) : Player(mPaint, rectF) {

    private var relativePosition = 0F
    override var hitPoints = 3
    override var living = false

    override fun updatePosition(x: Float, y: Float) {
        xPos = updateRelativePosition(x)
        if(yPos < 100){
            yPos += 10
        }
            rectF.set(xPos, yPos, xPos + mWith, yPos + mHeight)
            rectF.offset(-mWith / 2, -mHeight / 2)
    }

    private fun updateRelativePosition(playerPosition:Float):Float{
        relativePosition += 0.1F
        return (playerPosition + kotlin.math.sin(
            relativePosition.rem(360)
        ) * 100)
    }

    fun getMineSpawnPosition(): Pair<Int, Int> {
        return Pair(
            (rectF.left + mWith / 2).toInt(),
            rectF.bottom.toInt()
        )
    }
}