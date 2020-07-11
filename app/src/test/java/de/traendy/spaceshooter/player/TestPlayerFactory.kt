package de.traendy.spaceshooter.player

import android.graphics.Paint
import android.graphics.RectF
import de.traendy.spaceshooter.engine.EntityFactory
import org.mockito.Mockito
import org.mockito.Mockito.mock

object TestPlayerFactory: EntityFactory<Player> {
    override fun create(): Player {
        return Player(
            mock(Paint::class.java),
            mock(RectF::class.java)
        )
    }
}