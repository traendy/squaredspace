package de.traendy.spaceshooter.weapon

import de.traendy.spaceshooter.engine.getAccelerateDecelerateInterpolator
import de.traendy.spaceshooter.engine.getDecelerateInterpolation
import de.traendy.spaceshooter.game.GameConfig
import de.traendy.spaceshooter.game.OldGameState

class Overcharge(private var oldGameState: OldGameState) {
    private var interpolatorStart = 1.1f
    private var interpolatorEnd = 1.1f
    private var interpolationSpeed = 0.01f
    private var currentAttacKSpeed = GameConfig.attackSpeed
    private var newAttackSpeed = GameConfig.attackSpeed

    fun reset(currentAttacKSpeed: Long, newAttackSpeed: Long) {
        interpolatorStart = 0.0f
        interpolatorEnd = 0.0f
        this.currentAttacKSpeed = currentAttacKSpeed
        this.newAttackSpeed = newAttackSpeed
    }

    fun overcharge(): Boolean {
        if (interpolatorStart <= 1.0f) {
            oldGameState.projectileSpawningInterval =
                currentAttacKSpeed - ((currentAttacKSpeed - 50) * getDecelerateInterpolation(
                    interpolatorStart, 2f
                )).toInt()
            interpolatorStart += interpolationSpeed
            return true
        } else{
            if (interpolatorEnd <= 1.0f) {
                oldGameState.projectileSpawningInterval =
                    50L + ((newAttackSpeed - 50) * getAccelerateDecelerateInterpolator(
                        interpolatorEnd
                    )).toInt()
                interpolatorEnd += interpolationSpeed
                return true
            }
        }
        return false
    }
}