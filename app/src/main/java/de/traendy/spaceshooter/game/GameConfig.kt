package de.traendy.spaceshooter.game

import kotlin.random.Random

object GameConfig {
    const val meteorBaseSize = 50f
    const val meteorBaseVelocity = 10f
    const val meteorMinimalSplitRadius = 25f
    const val meteorBorderColor = "#FFAA55"
    const val meteorFillColor = "#AAFFAA55"
    const val attackSpeed = 508L
    const val starSizeRange = 20
    const val starSpawnRange = 3
    const val frameRate = 16L
    const val damageLightningEffectSpeed = 0.1f
    const val bossPoints = 200
    const val bossLoseParticles = 5
    const val invulnerabilityDuration = 3000L
    const val mineSpawnInterval = 750L
    const val bossSpawnInterval = 60 * 1000 * 16 / 8L / 8
    const val meteorSpawnInterval = 512L
    const val starSpawnInterval = 208L
    const val powerUpSpawnInterval = 5008L
    const val maxAttackSpeed = 256
    const val meteorSizeRange = 150
    const val meteorPoints = 10
    const val meteorDamageOffset = 30f
    const val bossSize = 150f
    const val bossHitPoints = 10
    const val mineSize = 60f
    const val mineVelocity = 10
    const val playerHitPoints = 3
    const val powerUpSize = 60f
    const val powerUpSpeed = 10f
    const val attackSpeedPowerUpPoints = 10
    const val healthPowerUpPoints = 50
    const val pointsPowerUpPoints = 100
    const val attackSpeedPowerUpAmplification = 8
    const val powerUpHealthSpawnOffset = 0.3f
    const val powerUpAttackSpeedSpawnOffset = 0.6f
    const val projectileHeight = 50f
    const val projectileWidth = 5f
    const val projectileSpeed = -15
    const val meteorSpawnAmplification = 100L

}