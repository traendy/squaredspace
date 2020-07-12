package de.traendy.spaceshooter.game

import kotlin.random.Random

object GameConfig {
    const val meteorBaseSize = 50f
    const val meteorBaseVelocity = 10f
    const val meteorMinimalSplitRadius = 100f
    const val meteorBorderColor = "#FFAA55"
    const val meteorFillColor = "#AAFFAA55"


    public fun getScaledRandonModificator(value:Float):Float = Random.nextDouble(value.toDouble()).toFloat()
}