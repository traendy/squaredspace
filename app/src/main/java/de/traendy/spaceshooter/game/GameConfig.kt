package de.traendy.spaceshooter.game

import kotlin.random.Random

object GameConfig {
    val meteorBaseSize = 50f
    val meteorBaseVelocity = 10f
    val meteorMinimalSplitRadius = 100f
    val meteorBorderColor = "#FFAA55"
    val meteorFillColor = "#AAFFAA55"


    public fun getScaledRandonModificator(value:Float):Float = Random.nextDouble(value.toDouble()).toFloat()
}