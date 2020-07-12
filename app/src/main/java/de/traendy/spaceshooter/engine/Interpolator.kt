package de.traendy.spaceshooter.engine

import kotlin.math.cos
import kotlin.math.sin

/**
 * These Interpolators expect an input Value between 0.0 and 1.0 and return the expected progress between 0.0 and 1.0
 * They are taken from the animation package of android.
 */
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

fun getCircleInterpolator(input: Float, circles: Float): Float {
    return sin(2 * circles * Math.PI * input).toFloat()
}

fun getOvershootInterpolator(input: Float, tension: Float): Float {
    var t = input
    t -= 1.0f
    return t * t * ((tension + 1) * t + tension) + 1.0f
}