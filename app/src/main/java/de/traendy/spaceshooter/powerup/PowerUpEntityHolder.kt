package de.traendy.spaceshooter.powerup

import android.graphics.Canvas
import de.traendy.spaceshooter.effects.PointsEntityHolder
import de.traendy.spaceshooter.engine.*
import de.traendy.spaceshooter.game.GameConfig
import de.traendy.spaceshooter.game.OldGameState
import de.traendy.spaceshooter.player.Player
import de.traendy.spaceshooter.weapon.Overcharge
import kotlin.random.Random

class PowerUpEntityHolder(
    private val collisionDetector: CollisionDetector,
    private val spawner: Spawner,
    private val oldGameState: OldGameState
) : PrimitiveEntityHolder<Entity>() {

    fun spawnPowerUp(spawnY: Int, spawnX: Int, hitPoints: Int) {
        if (spawner.spawn()) {
            val pointer = getDecelerateInterpolation(
                Random.nextFloat(), 1f
            )
            when {
                pointer < GameConfig.powerUpHealthSpawnOffset -> {
                    val powerUp = HealthPowerUp(
                        spawnY.toFloat(),
                        Random.nextInt(spawnX).toFloat()
                    )
                    prepareEntityAddition(powerUp)
                }
                pointer < GameConfig.powerUpAttackSpeedSpawnOffset -> {
                    val powerUp = AttackSpeedPowerUp(
                        spawnY.toFloat(),
                        Random.nextInt(spawnX).toFloat()
                    )
                    prepareEntityAddition(powerUp)
                }
                else -> {
                    val powerUp = PointsPowerUp(
                        spawnY.toFloat(),
                        Random.nextInt(spawnX).toFloat()
                    )
                    prepareEntityAddition(powerUp)
                }
            }
        }
    }

    fun updatePowerUps(
        player: Player?, canvas: Canvas,
        pointsEntityHolder: PointsEntityHolder,
        overcharge: Overcharge
    ) {
        getAllEntities().forEach { powerUp ->
            if (!powerUp.isAlive()) {
                prepareEntityDeletion(powerUp)
            } else {
                powerUp.updatePosition(0f, 0f)
                powerUp.draw(canvas)
                detectPlayerCollision(player, powerUp, pointsEntityHolder, overcharge)
            }
        }
    }

    private fun detectPlayerCollision(
        player: Player?,
        powerUp: Entity,
        pointsEntityHolder: PointsEntityHolder,
        overcharge: Overcharge

    ) {
        player?.let {
            if (collisionDetector.collided(
                    powerUp,
                    player
                ) && powerUp.isAlive() && player.isAlive()
            ) {
                powerUp.kill()
                var points = 0
                when (powerUp) {
                    is AttackSpeedPowerUp -> {

                        points = GameConfig.attackSpeedPowerUpPoints
                        overcharge.reset(oldGameState.projectileSpawningInterval,
                            (oldGameState.projectileSpawningInterval - GameConfig.attackSpeedPowerUpAmplification))
                    }
                    is HealthPowerUp -> {
                        if (player.hitPoints < GameConfig.playerHitPoints) {
                            player.hitPoints++
                        }
                        points = GameConfig.healthPowerUpPoints
                    }
                    is PointsPowerUp -> {
                        points = GameConfig.pointsPowerUpPoints + ((System.currentTimeMillis() - oldGameState.startTime) / 1000).toInt()
                    }
                }
                oldGameState.addPoint(points)
                pointsEntityHolder.spawnPoints(powerUp.xPos, powerUp.yPos, points)
            }
        }
    }
}