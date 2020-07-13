package de.traendy.spaceshooter.obstacle

import android.graphics.Canvas
import de.traendy.spaceshooter.engine.CollisionDetector
import de.traendy.spaceshooter.engine.Entity
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import de.traendy.spaceshooter.engine.Spawner
import de.traendy.spaceshooter.game.GameState
import de.traendy.spaceshooter.player.Player
import kotlin.random.Random


class MeteorEntityHolder(
    private val collisionDetector: CollisionDetector,
    private val spawner: Spawner,
    private val gameState: GameState
) : PrimitiveEntityHolder<Meteor>() {

    fun spawnMeteors(spawnY: Int, spawnX: Int) {
        if (spawner.spawn()) {
            for (i in 0..Random.nextInt(2)) {
                val meteor = Meteor(
                    spawnY,
                    Random.nextInt(spawnX).toFloat()
                )
                prepareEntityAddition(meteor)
            }
        }
    }

    fun updateMeteors(dangerousEntities: List<Entity>, player: Player?, canvas: Canvas) {
        getAllEntities().forEach { meteor ->
            detectDestruction(dangerousEntities, meteor)
            if (!meteor.isAlive()) {
                prepareEntityDeletion(meteor)
            } else {
                meteor.updatePosition(0f, 0f)
                meteor.draw(canvas)
                detectPlayerCollision(player, meteor)
            }
        }
    }

    private fun detectPlayerCollision(
        player: Player?,
        meteor: Meteor
    ) {
        player?.let {
            if (collisionDetector.collided(meteor, player) && meteor.isAlive()) {
                meteor.kill()
                player.kill()
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
                gameState.addPoint()
            }
        }
    }

}