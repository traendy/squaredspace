package de.traendy.spaceshooter.powerup

import android.graphics.Canvas
import de.traendy.spaceshooter.engine.*
import de.traendy.spaceshooter.game.OldGameState
import de.traendy.spaceshooter.player.Player
import kotlin.random.Random

class PowerUpEntityHolder(
    private val collisionDetector: CollisionDetector,
    private val spawner: Spawner,
    private val oldGameState: OldGameState
) : PrimitiveEntityHolder<Entity>() {

    fun spawnPowerUp(spawnY: Int, spawnX: Int, hitPoints: Int) {
        if (spawner.spawn()) {
            val pointer = getDecelerateInterpolation(
                Random.nextFloat(),1f)
            if(pointer < 0.3 ) {
                val powerUp = AttackSpeedPowerUp(
                    spawnY.toFloat(),
                    Random.nextInt(spawnX).toFloat()
                )
                prepareEntityAddition(powerUp)
            }else if (pointer < 0.6) {
                val powerUp = HealthPowerUp(
                    spawnY.toFloat(),
                    Random.nextInt(spawnX).toFloat()
                )
                prepareEntityAddition(powerUp)
            }else{
                val powerUp = PointsPowerUp(
                    spawnY.toFloat(),
                    Random.nextInt(spawnX).toFloat()
                )
                prepareEntityAddition(powerUp)
            }


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
        powerUp: Entity
    ) {
        player?.let {
            if (collisionDetector.collided(powerUp, player) && powerUp.isAlive() && player.isAlive()) {
                powerUp.kill()
                if(powerUp is AttackSpeedPowerUp){
                    oldGameState.projectileSpawningInterval -= 8
                }else if (powerUp is HealthPowerUp){
                    if(player.hitPoints <3){
                        player.hitPoints++
                    } else {
                        oldGameState.addPoint(50)
                    }
                }else if( powerUp is PointsPowerUp){
                    oldGameState.addPoint(100)
                }

            }
        }
    }
}