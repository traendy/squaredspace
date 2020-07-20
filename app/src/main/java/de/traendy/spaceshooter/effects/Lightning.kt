package de.traendy.spaceshooter.effects

import android.graphics.*
import de.traendy.spaceshooter.engine.getCircleInterpolator
import kotlin.math.abs

class Lightning(private val animationSpeed: Float = 0.01f, private val color: ColorARGB = getWhite(), private val state:State? = null) {

    private var interpolatorPosition = 1.0f
    private var isActive = false;

    public fun show() {
        isActive = true
        interpolatorPosition = 0.0f
        state?.start()
    }

    public fun draw(canvas: Canvas) {
        if (interpolatorPosition < 1.0f && isActive) {
            if(abs(interpolatorPosition - 0.5) < animationSpeed){
                state?.half()
            }
            canvas.drawARGB(
                (color.alpha * getCircleInterpolator(
                    interpolatorPosition,
                    0.5f
                )).toInt(), color.red, color.green, color.blue
            )
            interpolatorPosition += animationSpeed
        }else{
            isActive = false
            state?.done()
        }
    }

    interface State{
        fun start()
        fun half()
        fun done()
    }
}