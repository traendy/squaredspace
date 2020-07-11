package de.traendy.spaceshooter.player

import de.traendy.spaceshooter.engine.PrimitiveCollisionDetector
import de.traendy.spaceshooter.weapon.Projectile

class BossProjectileCollisionDetector {

    val primitiveCollisionDetector = PrimitiveCollisionDetector()

    fun collided(boss:Boss, projectiles: List<Projectile>):Boolean{
        for (projectile in projectiles){
            if(projectile.isAlive() && boss.isAlive()){
                if (primitiveCollisionDetector.collided(projectile, boss)) {
                    projectile.kill()
                    boss.kill()
                    return true
                }
            }

        }
        return false
    }

}