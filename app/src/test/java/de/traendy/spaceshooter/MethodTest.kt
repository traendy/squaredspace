package de.traendy.spaceshooter

import org.junit.Test
import kotlin.math.cos

class MethodTest {
    @Test
    fun interpolatorTest() {

        val times = 10
        val value = 1 / times.toFloat()
        println("AccelerateDecelerateInterpolator:  $value")
        for (i in 0..times) {
            val output = getAccelerateDecelerateInterpolator(value * i)
            println("${value * i},$output")
        }

        println("DecelerateInterpolator:  $value 1.0")
        for (i in 0..times) {
            val output = getDecelerateInterpolation(value * i, 1.0f)
            println("${value * i},$output")
        }

        println("DecelerateInterpolator:  $value 2.0")
        for (i in 0..times) {
            val output = getDecelerateInterpolation(value * i, 2.0f)
            println("${value * i},$output")
        }

        println("AcceleratorInterpolator:  $value 0.0")
        for (i in 0..times) {
            val output = getAnticipateInterpolator(value * i, 0.0f)
            println("${value * i},$output")
        }

        println("AnticipateInterpolator:  $value 1.0")
        for (i in 0..times) {
            val output = getAnticipateInterpolator(value * i, 1.0f)
            println("${value * i},$output")
        }

        println("AnticipateInterpolator:  $value 2.0")
        for (i in 0..times) {
            val output = getAnticipateInterpolator(value * i, 2.0f)
            println("${value * i},$output")
        }

        println("AnticipateInterpolator:  $value -1.0")
        for (i in 0..times) {
            val output = getAnticipateInterpolator(value * i, -1.0f)
            println("${value * i},$output")
        }

        println("AnticipateOvershootInterpolator:  $value 1.0")
        for (i in 0..times) {
            val output = getAnticipateOvershootInterpolator(value * i, 1.0f)
            println("${value * i},$output")
        }

        println("BounceInterpolator:")
        for (i in 0..times) {
            val output = getBounceInterpolator(value * i)
            println("${value * i},$output")
        }

        println("CircleInterpolator:  $value 1.0")
        for (i in 0..times) {
            val output = getCircleInterpolator(value * i, 1.0f)
            println("${value * i},$output")
        }

        println("CircleInterpolator:  $value 2.0")
        for (i in 0..times) {
            val output = getCircleInterpolator(value * i, 2.0f)
            println("${value * i},$output")
        }

        println("OvershootInterpolator:  $value 1.0")
        for (i in 0..times) {
            val output = getOvershootInterpolator(value * i, 1.0f)
            println("${value * i},$output")
        }

        println("OvershootInterpolator:  $value 2.0")
        for (i in 0..times) {
            val output = getOvershootInterpolator(value * i, 2.0f)
            println("${value * i},$output")
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

    fun getAnticipateOvershootInterpolator(input: Float, tension: Float): Float {
        return if (input < 0.5f) 0.5f * a(
            input * 2.0f,
            tension
        ) else 0.5f * (o(input * 2.0f - 2.0f, tension) + 2.0f)
    }

    private fun a(t: Float, s: Float): Float {
        return t * t * ((s + 1) * t - s)
    }

    private fun o(t: Float, s: Float): Float {
        return t * t * ((s + 1) * t + s)
    }

    fun getBounceInterpolator(input: Float): Float {
        var t = input
        t *= 1.1226f
        return when {
            t < 0.3535f -> bounce(t)
            t < 0.7408f -> bounce(
                t - 0.54719f
            ) + 0.7f
            t < 0.9644f -> bounce(t - 0.8526f) + 0.9f
            else -> bounce(
                t - 1.0435f
            ) + 0.95f
        }
    }

    private fun bounce(t: Float): Float {
        return t * t * 8.0f
    }

    fun getCircleInterpolator(input: Float, circles:Float): Float {
        return Math.sin(2 * circles * Math.PI * input).toFloat()
    }

    fun getOvershootInterpolator(input: Float, tension:Float): Float {
        var t = input
        t -= 1.0f
        return t * t * ((tension + 1) * t + tension) + 1.0f
    }
}