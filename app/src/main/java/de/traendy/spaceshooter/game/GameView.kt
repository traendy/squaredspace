package de.traendy.spaceshooter.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Picture
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.scale
import de.traendy.spaceshooter.R
import de.traendy.spaceshooter.effects.Lightning
import de.traendy.spaceshooter.engine.FrameRate
import de.traendy.spaceshooter.engine.PrimitiveCollisionDetector
import de.traendy.spaceshooter.engine.Spawner
import de.traendy.spaceshooter.enviroment.StarEntityHolder
import de.traendy.spaceshooter.hud.MetaHud
import de.traendy.spaceshooter.obstacle.MeteorEntityHolder
import de.traendy.spaceshooter.player.*
import de.traendy.spaceshooter.powerup.PowerUpEntityHolder
import de.traendy.spaceshooter.weapon.ProjectileEntityHolder
import java.util.*


private const val TAG = "GameView"

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), Runnable, Observer {

    private var mSurfaceHolder: SurfaceHolder = holder
    private var mViewWidth: Int = 0
    private var mViewHeight: Int = 0
    private lateinit var mGameThread: Thread
    private var mRunning: Boolean = false

//    private val player = PlayerFactory.create()

    private val player = PlayerFactory.create(getBitmapFromVectorDrawable(context, R.drawable.spaceship))
    private fun getBitmapFromVectorDrawable(
        context: Context?,
        drawableId: Int
    ): Bitmap? {
        var drawable =
            ContextCompat.getDrawable(context!!, drawableId)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private val frameRate = FrameRate(16L)
    val gameState =
        GameState(System.currentTimeMillis())
    private val meteorSpawner = Spawner(gameState.meteorSpawningInterval)
    private val meteorEntityHolder =
        MeteorEntityHolder(
            PrimitiveCollisionDetector(),
            meteorSpawner,
            gameState
        )
    private val projectileSpawner = Spawner(gameState.projectileSpawningInterval)
    private val projectileEntityHolder =
        ProjectileEntityHolder(
            projectileSpawner
        )
    private val powerUpSpawner = Spawner(gameState.powerUpSpawningInterval)
    private val powerUpEntityHolder =
        PowerUpEntityHolder(
            PrimitiveCollisionDetector(),
            powerUpSpawner,
            gameState
        )
    private val starSpawner = Spawner(gameState.starSpawningInterval)
    private val starEntityHolder =
        StarEntityHolder(starSpawner)
    private val playerParticleEntityHolder =
        ParticleEntityHolderFactory.createPlayerParticleHolder()

    private val bossParticleEntityHolder = ParticleEntityHolderFactory.createBossParticleHolder()
    private val bossSpawner = Spawner(gameState.bossSpawningInterval)
    private val boss = PlayerFactory.createBoss().apply { kill() }
    private val bossProjectileCollisionDetector = BossProjectileCollisionDetector()
    private val mineEntityHolder = MineEntityHolder(Spawner(gameState.mineSpawningInterval))
    private var numberOfMeteors = 0
    private val metaHud = MetaHud()
    private val startLightning = Lightning()
    private val damageLightning = Lightning(0.1f, 50)
    private val playerInvulnerability = Invulnerability()

    init {
        gameState.addObserver(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mViewWidth = w
        mViewHeight = h
    }

    override fun run() {
        while (mRunning) {
            if (mSurfaceHolder.surface.isValid && frameRate.isNextFrame(System.currentTimeMillis())) {
                val canvas = mSurfaceHolder.lockCanvas()
                canvas.save()
                canvas.drawColor(
                    ContextCompat.getColor(
                        context,
                        R.color.surfaceBackground
                    )
                )
                drawStars(canvas)
                drawPowerUps(canvas)
                drawMeteors(canvas)
                if (gameState.running && isPlayerAlive(player, gameState)) {
                    drawProjectiles(canvas)
                    player.draw(canvas)
                    drawBoss(canvas)
//                    drawPlayerParticles(canvas)
                    drawBossParticles(canvas)
                    drawHud(canvas)
                    damageLightning.draw(canvas)
                }
                startLightning.draw(canvas)
                canvas.restore()
                mSurfaceHolder.unlockCanvasAndPost(canvas)

                powerUpEntityHolder.executePreparedDeletion()
                powerUpEntityHolder.executePreparedAddition()
                meteorEntityHolder.executePreparedDeletion()
                meteorEntityHolder.executePreparedAddition()
                projectileEntityHolder.executePreparedDeletion()
                projectileEntityHolder.executePreparedAddition()
                starEntityHolder.executePreparedDeletion()
                starEntityHolder.executePreparedAddition()
                playerParticleEntityHolder.executePreparedDeletion()
                playerParticleEntityHolder.executePreparedAddition()
                bossParticleEntityHolder.executePreparedDeletion()
                bossParticleEntityHolder.executePreparedAddition()
                mineEntityHolder.executePreparedAddition()
                mineEntityHolder.executePreparedDeletion()
                updateSpawner(gameState)
            }
        }
    }

    private fun drawHud(canvas: Canvas) {
        metaHud.updateScreenWidth(mViewWidth.toFloat())
        metaHud.drawHud(canvas, player.hitPoints, gameState, playerInvulnerability)
    }

    private fun isPlayerAlive(player: Player, gameState: GameState): Boolean {
        return if(player.isAlive()) true
        else {
            gameState.lose(System.currentTimeMillis())
            false
        }
    }

    private fun drawBoss(canvas: Canvas) {
        if (bossSpawner.spawn() && !boss.isAlive()) {
            boss.revive()
            bossParticleEntityHolder.reset()
        }
        if(bossProjectileCollisionDetector.collided(boss, projectileEntityHolder.getAllEntities())){
            gameState.addPoint(200)
            bossParticleEntityHolder.removeParticles(20)
        }
        if (!boss.isAlive()) {
            bossParticleEntityHolder.updateVisibility(false)
            mineEntityHolder.prepareEntityDeletion(mineEntityHolder.getAllEntities())
            bossSpawner.enable()
        } else {
            bossSpawner.disable()
            bossParticleEntityHolder.updateVisibility(true)
            boss.updatePosition(player.xPos, 100f)
            boss.draw(canvas)
            drawMines(canvas)
        }
    }

    private fun drawMines(canvas: Canvas) {
        mineEntityHolder.spawnMines(
            mViewHeight,
            boss.getMineSpawnPosition().first,
            boss.getMineSpawnPosition().second,
            boss
        )
        mineEntityHolder.updateMines(canvas, player, gameState, boss, damageLightning, playerInvulnerability)
    }

    private fun updateSpawner(gameState: GameState) {
        projectileSpawner.updateInterval(gameState.projectileSpawningInterval)
        meteorSpawner.updateInterval(gameState.meteorSpawningInterval)
        starSpawner.updateInterval(gameState.starSpawningInterval)
        powerUpSpawner.updateInterval(gameState.powerUpSpawningInterval)
        bossSpawner.updateInterval(gameState.bossSpawningInterval)
    }

    private fun drawPlayerParticles(canvas: Canvas) {
        playerParticleEntityHolder.updatePosition(player.xPos, player.yPos)
        playerParticleEntityHolder.draw(canvas)
    }

    private fun drawBossParticles(canvas: Canvas) {
        bossParticleEntityHolder.updatePosition(boss.xPos, boss.yPos)
        bossParticleEntityHolder.draw(canvas)
    }

    private fun drawPowerUps(canvas: Canvas) {
        powerUpEntityHolder.spawnPowerUp(mViewHeight, mViewWidth)
        powerUpEntityHolder.updatePowerUps(player, canvas)
    }

    private fun drawStars(canvas: Canvas) {
        starEntityHolder.spawnStars(mViewHeight.toFloat(), mViewWidth.toFloat())
        starEntityHolder.updateStars(canvas)
    }

    private fun drawProjectiles(canvas: Canvas) {
        if (gameState.running) {
            projectileEntityHolder.spawnProjectiles(
                player.getProjectileSpawnPosition().first,
                player.getProjectileSpawnPosition().second
            )
        }
        projectileEntityHolder.updateProjectiles(canvas)
    }

    private fun drawMeteors(canvas: Canvas) {
        meteorEntityHolder.spawnMeteors(mViewHeight, mViewWidth)
        meteorEntityHolder.updateMeteors(
            projectileEntityHolder.getAllEntities(),
            player,
            canvas,
            damageLightning,
            playerInvulnerability
        )
        numberOfMeteors = meteorEntityHolder.getAllEntities().size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        //check if player is clicked
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                player.updatePosition(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
                player.updatePosition(x, y)
            }
        }
        return true
    }

    fun pause() {
        mRunning = false
        try {
            // Stop the thread (rejoin the main thread)
            mGameThread.join()
        } catch (e: InterruptedException) {
            Log.e(TAG, "$e.message")
        }
    }

    fun resume() {
        mRunning = true
        mGameThread = Thread(this)
        mGameThread.start()
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is GameState) {
            if (!o.running) {
                projectileEntityHolder.prepareEntityDeletion(projectileEntityHolder.getAllEntities())
                playerParticleEntityHolder.updateVisibility(false)
                projectileSpawner.disable()
                bossSpawner.disable()
                repeat(3){
                    boss.kill()
                }
            } else {
                playerInvulnerability.activateInvulnerability(System.currentTimeMillis(), 3000L)
                player.revive()
                startLightning.show()
                bossSpawner.enable()
                projectileSpawner.enable()
                meteorEntityHolder.prepareEntityDeletion(meteorEntityHolder.getAllEntities())
                player.setSpawn(mViewWidth / 2f, mViewHeight - 120f)
                playerParticleEntityHolder.updateVisibility(true)
            }
        }
    }
}


