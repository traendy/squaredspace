package de.traendy.spaceshooter.effects

import android.graphics.*
import de.traendy.spaceshooter.engine.getCircleInterpolator

class Lightning(private val animationSpeed:Float = 0.01f, private val strength: Int = 255) {

    private var interpolatorPosition = 1.0f


    public fun show(){
        interpolatorPosition = 0.0f
    }

    public fun draw(canvas: Canvas){
        if(interpolatorPosition < 1.0f) {
            canvas.drawARGB((strength*getCircleInterpolator(interpolatorPosition, 0.5f)).toInt(), 255,255,255)
            interpolatorPosition += animationSpeed
        }
    }

}