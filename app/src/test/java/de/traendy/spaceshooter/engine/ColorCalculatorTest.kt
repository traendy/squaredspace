package de.traendy.spaceshooter.engine

import android.graphics.Color
import org.junit.Test

class ColorCalculatorTest {


    @Test
    fun testColorCalculation(){
        val result = calculateColor(0.2f, 0.5f, ::getCircleInterpolator)
        println(result)

    }


    private fun calculateColor(interpolatorPosition:Float, interpolatorParam:Float, interpolator: (Float, Float)->Float):String {
        val alpha = 255 * interpolator(interpolatorPosition, interpolatorParam)
        val alphaString = Integer.toHexString(alpha.toInt())
        return "#${alphaString}FFFFFF"
    }

}