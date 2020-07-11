package de.traendy.spaceshooter

import org.junit.Test
import kotlin.math.cos

class MethodTest {
    @Test
    fun interpolatorTest() {

        val times = 10
        var value = 1/times.toFloat()
        println("AccelerateDecelerateInterpolator:  $value")
        for(i in 1..times){
            val output = getAccelerateDecelerateInterpolator(value*i)
            println("Value: $i: Output: $output")
        }

        println("DecelerateInterpolator:  $value 1.0")
        for (i in 1..times) {
            val output = getDecelerateInterpolation(value * i, 1.0f)
            println("Value: $i: Output: $output")
        }

        println("DecelerateInterpolator:  $value 2.0")
        for (i in 1..times) {
            val output = getDecelerateInterpolation(value * i, 2.0f)
            println("Value: $i: Output: $output")
        }

        println("AnticipateInterpolator:  $value 0.0")
        for (i in 1..times) {
            val output = getAnticipateInterpolator(value*i, 0.0f)
            println("Value: $i: Output: $output")
        }

        println("AnticipateInterpolator:  $value 1.0")
        for (i in 1..times) {
            val output = getAnticipateInterpolator(value * i, 1.0f)
            println("Value: $i: Output: $output")
        }

        println("AnticipateInterpolator:  $value 2.0")
        for (i in 1..times) {
            val output = getAnticipateInterpolator(value * i, 2.0f)
            println("Value: $i: Output: $output")
        }

        println("AnticipateInterpolator:  $value -1.0")
        for (i in 1..times) {
            val output = getAnticipateInterpolator(value * i, -1.0f)
            println("Value: $i: Output: $output")
        }

    }

    fun getDecelerateInterpolation(input: Float, factor: Float): Float {
        return if (factor == 1.0f) {
            (1.0f - (1.0f - input) * (1.0f - input))
        } else {
            (1.0f - Math.pow(
                (1.0f - input).toDouble(),
                2 * factor.toDouble()
            )).toFloat()
        }
    }

    fun getAccelerateDecelerateInterpolator(input: Float): Float {
        return (cos((input + 1) * Math.PI) / 2.0f).toFloat() + 0.5f
    }

    /**
     * @param tension Amount of anticipation. When tension equals 0.0f, there is
     *                no anticipation and the interpolator becomes a simple
     *                acceleration interpolator.
     */
    fun getAnticipateInterpolator(input: Float, tension: Float): Float {
        return input * input * ((tension + 1) * input - tension)
    }

}