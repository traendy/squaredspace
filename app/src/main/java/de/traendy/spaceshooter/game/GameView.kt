package de.traendy.spaceshooter.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import de.traendy.spaceshooter.R
import de.traendy.spaceshooter.effects.*
import de.traendy.spaceshooter.engine.FrameRate
import de.traendy.spaceshooter.engine.PrimitiveCollisionDetector
import de.traendy.spaceshooter.engine.Spawner
import de.traendy.spaceshooter.enviroment.StarEntityHolder
import de.traendy.spaceshooter.hud.MetaHud
import de.traendy.spaceshooter.obstacle.MeteorEntityHolder
import de.traendy.spaceshooter.player.*
import de.traendy.spaceshooter.powerup.PowerUpEntityHolder
import de.traendy.spaceshooter.weapon.Overcharge
import de.traendy.spaceshooter.weapon.ProjectileEntityHolder


private const val TAG = "GameView"

class GameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), Runnable, /*Observer,*/ StateMediator.Listener {

    private var mSurfaceHolder: SurfaceHolder = holder
    private var mViewWidth: Int = 0
    private var mViewHeight: Int = 0
    private lateinit var mGameThread: Thread
    private var mRunning: Boolean = false

    private val player =
        PlayerFactory.create(getBitmapFromVectorDrawable(context, R.drawable.spaceship))

    private fun getBitmapFromVectorDrawable(
        context: Context?,
        drawableId: Int
    ): Bitmap? {
        val drawable =
            ContextCompat.getDrawable(context!!, drawableId)
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private val frameRate = FrameRate(GameConfig.frameRate)
    val gameState =
        OldGameState(System.currentTimeMillis())
    private val meteorSpawner = Spawner(GameConfig.meteorSpawnInterval)
    private val meteorEntityHolder =
        MeteorEntityHolder(
            PrimitiveCollisionDetector(),
            meteorSpawner,
            gameState
        )
    private val overCharge = Overcharge(gameState)
    private val projectileSpawner = Spawner(gameState.projectileSpawningInterval)
    private val projectileEntityHolder =
        ProjectileEntityHolder(
            projectileSpawner
        )
    private val powerUpSpawner = Spawner(GameConfig.powerUpSpawnInterval)
    private val powerUpEntityHolder =
        PowerUpEntityHolder(
            PrimitiveCollisionDetector(),
            powerUpSpawner,
            gameState
        )
    private val starSpawner = Spawner(GameConfig.starSpawnInterval)
    private val starEntityHolder =
        StarEntityHolder(starSpawner)


    private val bossParticleEntityHolder = ParticleEntityHolderFactory.createBossParticleHolder()
    private val bossSpawner = Spawner(GameConfig.bossSpawnInterval)
    private val boss = PlayerFactory.createBoss().apply { kill() }
    private val bossProjectileCollisionDetector = BossProjectileCollisionDetector()
    private val mineEntityHolder = MineEntityHolder(Spawner(GameConfig.mineSpawnInterval))
    private val pointsEntityHolder = PointsEntityHolder()
    private val metaHud = MetaHud()
    private val startLightning = Lightning(state = object : Lightning.State {
        override fun start() { /* unused */
        }

        override fun half() { /* unused */
        }

        override fun done() {
            StateMediator.progressState()
        }
    })
    private val damageLightning = Lightning(GameConfig.damageLightningEffectSpeed, getWhite50())
    private val endGameBlend = Lightning(color = getBlack(), state = object : Lightning.State {
        override fun start() { /* unused */
        }

        override fun half() { /* unused */
        }

        override fun done() {
            StateMediator.progressState()
        }

    })

    private val playerInvulnerability = Invulnerability()

    init {
        StateMediator.register(this)
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
                drawMeteors(canvas)
                when (StateMediator.getState()) {
                    is Menu -> {

                    }
                    is GameStart -> {
                        startLightning.draw(canvas)
                        player.draw(canvas)
                        player.updatePositionBeforeGameStart(mViewWidth / 2f)

                    }
                    is GameRunning -> {
                        drawPowerUps(canvas)

                        if (gameState.running && isPlayerAlive(player, gameState)) {
                            overCharge.overcharge()
                            drawProjectiles(canvas)

                            pointsEntityHolder.draw(canvas)
                            player.draw(canvas)
                            drawBoss(canvas)
                            drawBossParticles(canvas)
                            drawHud(canvas)
                            damageLightning.draw(canvas)
                        }
                    }
                    is GameEnd -> {
                        endGameBlend.draw(canvas)
                    }
                }

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
                bossParticleEntityHolder.executePreparedDeletion()
                bossParticleEntityHolder.executePreparedAddition()
                mineEntityHolder.executePreparedAddition()
                mineEntityHolder.executePreparedDeletion()

                pointsEntityHolder.clean()
                pointsEntityHolder.executePreparedDeletion()
                pointsEntityHolder.executePreparedAddition()
                updateSpawner(gameState)
            }
        }
    }

    private fun drawHud(canvas: Canvas) {
        metaHud.updateScreenWidth(mViewWidth.toFloat())
        metaHud.drawHud(canvas, player.hitPoints, gameState, playerInvulnerability)
    }

    private fun isPlayerAlive(player: Player, oldGameState: OldGameState): Boolean {
        return if (player.isAlive()) true
        else {
            oldGameState.lose(System.currentTimeMillis())
            false
        }
    }

    private fun drawBoss(canvas: Canvas) {
        if (bossSpawner.spawn() && !boss.isAlive()) {
            boss.revive()
            bossParticleEntityHolder.reset()
        }
        if (bossProjectileCollisionDetector.collided(
                boss,
                projectileEntityHolder.getAllEntities()
            )
        ) {
            gameState.addPoint(GameConfig.bossPoints)
            pointsEntityHolder.spawnPoints(boss.xPos, boss.yPos, GameConfig.bossPoints)
            bossParticleEntityHolder.removeParticles(GameConfig.bossLoseParticles)
        }
        if (!boss.isAlive()) {
            bossParticleEntityHolder.updateVisibility(false)
            bossSpawner.enable()
        } else {
            bossSpawner.disable()
            bossParticleEntityHolder.updatePaint(boss.getRandomPaint())
            bossParticleEntityHolder.updateVisibility(true)
            boss.updatePosition(player.xPos, 100f)
            boss.draw(canvas)
            mineEntityHolder.spawnMines(
                mViewHeight,
                boss.getMineSpawnPosition().first,
                boss.getMineSpawnPosition().second,
                boss
            )
        }
        mineEntityHolder.updateMines(
            canvas,
            player,
            damageLightning,
            playerInvulnerability
        )
    }

    private fun updateSpawner(oldGameState: OldGameState) {
        projectileSpawner.updateInterval(oldGameState.projectileSpawningInterval)
        val spawnUpdate =
            GameConfig.meteorSpawnInterval - oldGameState.highScore() / GameConfig.meteorSpawnAmplification
        meteorSpawner.updateInterval(spawnUpdate)
        starSpawner.updateInterval(GameConfig.starSpawnInterval)
        powerUpSpawner.updateInterval(GameConfig.powerUpSpawnInterval)
        bossSpawner.updateInterval(GameConfig.bossSpawnInterval)
    }

    private fun drawBossParticles(canvas: Canvas) {
        bossParticleEntityHolder.updatePosition(boss.xPos, boss.yPos)
        bossParticleEntityHolder.draw(canvas)
    }

    private fun drawPowerUps(canvas: Canvas) {
        powerUpEntityHolder.spawnPowerUp(mViewHeight, mViewWidth, player.hitPoints)
        powerUpEntityHolder.updatePowerUps(player, canvas, pointsEntityHolder, overCharge)
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
            playerInvulnerability,
            pointsEntityHolder
        )
    }

    override fun onStateChange() {
        when (StateMediator.getState()) {
            is Menu -> {
            }
            is GameStart -> {
                startLightning.show()
                meteorEntityHolder.prepareEntityDeletion(meteorEntityHolder.getAllEntities())
                playerInvulnerability.activateInvulnerability(
                    System.currentTimeMillis(),
                    GameConfig.invulnerabilityDuration
                )
                player.revive()
                player.setSpawn(mViewWidth / 2f, mViewHeight + 250f)
            }
            is GameRunning -> {
                bossSpawner.enable()
                projectileSpawner.enable()
            }
            is GameEnd -> {
                projectileEntityHolder.prepareEntityDeletion(projectileEntityHolder.getAllEntities())
                projectileSpawner.disable()
                bossSpawner.disable()
                repeat(boss.hitPoints) {
                    boss.kill()
                }
                mineEntityHolder.prepareEntityDeletion(mineEntityHolder.getAllEntities())
                powerUpEntityHolder.prepareEntityDeletion(powerUpEntityHolder.getAllEntities())
                pointsEntityHolder.prepareEntityDeletion(pointsEntityHolder.getAllEntities())
                endGameBlend.show()
            }
        }
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


}


