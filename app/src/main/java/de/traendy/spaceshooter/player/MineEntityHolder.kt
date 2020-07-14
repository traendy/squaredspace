package de.traendy.spaceshooter.player


import android.graphics.Canvas
import de.traendy.spaceshooter.effects.Lightning
import de.traendy.spaceshooter.engine.PrimitiveCollisionDetector
import de.traendy.spaceshooter.engine.PrimitiveEntityHolder
import de.traendy.spaceshooter.engine.Spawner
import de.traendy.spaceshooter.game.GameState
import kotlin.random.Random

class MineEntityHolder(private val spawner: Spawner): PrimitiveEntityHolder<Mine>() {

    fun spawnMines(worldHeight:Int, spawnY: Int, spawnX: Int, boss: Boss) {
        if (spawner.spawn() && boss.isAlive()) {
            val mine = Mine(
                worldHeight,
                spawnY.toFloat(),
                spawnX.toFloat(),
                Random.nextInt(-1, 2)
            )
            prepareEntityAddition(mine)
        }
    }

    fun updateMines(canvas: Canvas, player: Player, gameState: GameState, boss: Boss, damageLightning: Lightning) {
        if(!boss.isAlive()) {
            prepareEntityDeletion(getAllEntities())
        }
        getAllEntities().forEach { mine ->
            if(PrimitiveCollisionDetector().collided(mine, player) && mine.isAlive()){
                mine.kill()
                player.kill()
                damageLightning.show()
            }
            if (!mine.isAlive()) {
                prepareEntityDeletion(mine)
            } else {
                mine.updatePosition(0f, 0f)
                mine.draw(canvas)
            }
        }
    }
}
