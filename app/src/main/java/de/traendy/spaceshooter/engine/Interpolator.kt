package de.traendy.spaceshooter.engine

import kotlin.math.cos

/**
 * These Interpolators expect an input Value between 0.0 and 1.0 and return the expected progress between 0.0 and 1.0
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

/**
 * @param tension Amount of anticipation. When tension equals 0.0f, there is
 *                no anticipation and the interpolator becomes a simple
 *                acceleration interpolator.
 */
fun getAnticipateInterpolator(input: Float, tension: Float): Float {
    return input * input * ((tension + 1) * input - tension)
}