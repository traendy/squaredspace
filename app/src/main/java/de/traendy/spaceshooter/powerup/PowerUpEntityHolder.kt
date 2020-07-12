package de.traendy.spaceshooter.powerup

import android.graphics.Canvas
import de.traendy.spaceshooter.engine.CollisionDetector
import de.traendy.spaceshooter.game.GameState
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import de.traendy.spaceshooter.engine.Spawner
import de.traendy.spaceshooter.player.Player
import kotlin.random.Random

class PowerUpEntityHolder(
    private val collisionDetector: CollisionDetector,
    private val spawner: Spawner,
    private val gameState: GameState
) : PrimitiveEntityHolder<PowerUp>() {

    fun spawnPowerUp(spawnY: Int, spawnX: Int) {
        if (spawner.spawn()) {
            val powerUp = PowerUp(
                spawnY.toFloat(),
                Random.nextInt(spawnX).toFloat()
            )
            prepareEntityAddition(powerUp)

        }
    }

    fun updatePowerUps(player: Player?, canvas: Canvas) {
        getAllEntities().forEach { powerUp ->
            if (!powerUp.isAlive()) {
                prepareEntityDeletion(powerUp)
            } else {
                powerUp.updatePosition(0f, 0f)
                powerUp.draw(canvas)
                detectPlayerCollision(player, powerUp)
            }
        }
    }

    private fun detectPlayerCollision(
        player: Player?,
        powerUp: PowerUp
    ) {
        player?.let {
            if (collisionDetector.collided(powerUp, player) && powerUp.isAlive()) {
                powerUp.kill()
                gameState.addPoint(10)
                gameState.projectileSpawningInterval -= 16
            }
        }
    }
}