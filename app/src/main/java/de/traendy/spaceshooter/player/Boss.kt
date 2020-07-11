package de.traendy.spaceshooter.player

import android.graphics.Paint
import android.graphics.RectF

class Boss(mPaint: Paint, rectF: RectF) : Player(mPaint, rectF) {

    private var relativePosition = 0F
    override var hitPoints = 3
        get() = field
    protected override var living = false

    override fun updatePosition(x: Int, y: Int) {
        _X = updateRelativePosition(x).toInt()
        if(_Y < 100){
            _Y += 10
        }
            rectF.set(_X.toFloat(), _Y.toFloat(), _X + mWith, _Y + mHeight)
            rectF.offset(-mWith / 2, -mHeight / 2)
    }

    fun updateRelativePosition(playerPosition:Int):Float{
        relativePosition += 0.1F
        return (playerPosition + kotlin.math.sin(
            relativePosition.rem(360)
        ) * 100)
    }

    fun revive(){
        hitPoints = 3
        living = true
        _Y = -100
    }

    override fun kill() {
        hitPoints--
        if(hitPoints<= 0){
            super.kill()
        }
    }

    fun getMineSpawnPosition(): Pair<Int, Int> {
        return Pair(
            (rectF.left + mWith / 2).toInt(),
            rectF.bottom.toInt()
        )
    }
}