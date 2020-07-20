package de.traendy.spaceshooter.obstacle

import android.graphics.Canvas
import de.traendy.spaceshooter.effects.Lightning
import de.traendy.spaceshooter.engine.CollisionDetector
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import de.traendy.spaceshooter.engine.Spawner
import de.traendy.spaceshooter.game.OldGameState
import de.traendy.spaceshooter.player.Invulnerability
import de.traendy.spaceshooter.player.Player
import kotlin.random.Random


class MeteorEntityHolder(
    private val collisionDetector: CollisionDetector,
    private val spawner: Spawner,
    private val oldGameState: OldGameState
) : PrimitiveEntityHolder<Meteor>() {

    fun spawnMeteors(worldHeight: Int, worldWidth: Int) {
        if (spawner.spawn() && Random.nextBoolean()) {
            val meteor = Meteor(
                worldHeight,
                worldWidth,
                Random.nextInt(worldWidth).toFloat()
            )
            prepareEntityAddition(meteor)
        }
    }

    fun updateMeteors(
        dangerousEntities: List<Entity>,
        player: Player?,
        canvas: Canvas,
        damageEffect: Lightning,
        playerInvulnerability: Invulnerability
    ) {
        getAllEntities().forEach { meteor ->
            detectDestruction(dangerousEntities, meteor)
            if (!meteor.isAlive()) {
                prepareEntityDeletion(meteor)
            } else {
                meteor.updatePosition(0f, 0f)
                meteor.draw(canvas)
                detectPlayerCollision(player, meteor, damageEffect, playerInvulnerability)
            }
        }
    }

    private fun detectPlayerCollision(
        player: Player?,
        meteor: Meteor,
        damageEffect: Lightning,
        playerInvulnerability: Invulnerability
    ) {
        player?.let {
            if (collisionDetector.collided(
                    meteor,
                    player
                ) && meteor.isAlive() && player.isAlive() && playerInvulnerability.isVulnerable(
                    System.currentTimeMillis()
                )
            ) {
                meteor.kill()
                if (meteor.damage > 30f) {
                    player.kill()
                    damageEffect.show()
                    playerInvulnerability.activateInvulnerability(System.currentTimeMillis(), 3000L)
                }
            }
        }
    }

    private fun detectDestruction(
        dangerousEntities: List<Entity>,
        meteor: Meteor
    ) {
        dangerousEntities.forEach { danger ->
            if (collisionDetector.collided(danger, meteor)) {
                meteor.isShot()?.let { it -> prepareEntityAddition(it) }
                danger.kill()
                oldGameState.addPoint()
            }
        }
    }

}