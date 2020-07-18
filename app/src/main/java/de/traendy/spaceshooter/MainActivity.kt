package de.traendy.spaceshooter

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import de.traendy.spaceshooter.game.GameState
import de.traendy.spaceshooter.game.GameView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), Observer {

    private lateinit var mGameView: GameView
    private lateinit var mGameOverText: TextView
    private lateinit var mHighScoreLabel: TextView
    private lateinit var mHighScoreValue: TextView
    private lateinit var version: TextView
    private lateinit var thanks: TextView
    private lateinit var mStartButton: MaterialButton
    private lateinit var mHeadline: ImageView
    private var currentHighScore: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mGameView = findViewById(R.id.gameView)
        mGameOverText = findViewById(R.id.gameOverText)
        mHighScoreLabel = findViewById(R.id.highScoreLabel)
        mHighScoreValue = findViewById(R.id.highScoreValue)
        version = findViewById(R.id.version)
        thanks = findViewById(R.id.specialThanks)

        mStartButton = findViewById(R.id.startButton)
        mHeadline = findViewById(R.id.headline)
        mStartButton.setOnClickListener {
            resetGameState()
        }
        mGameView.gameState.addObserver(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_highscore_key), Context.MODE_PRIVATE
        )
        currentHighScore = sharedPref.getLong("LastHighScore", 0L)
        highScoreLabel.text = getString(R.string.current_highscore)
        highScoreValue.text = currentHighScore.toString()
        highScoreLabel.visibility = View.VISIBLE
        highScoreValue.visibility = View.VISIBLE
        version.visibility = View.VISIBLE
        version.text = BuildConfig.VERSION_NAME
        thanks.visibility = View.VISIBLE
        mHeadline.visibility = View.VISIBLE
    }

    private fun resetGameState() {
        mGameView.gameState.addObserver(this)
        mGameView.gameState.reset()
    }

    override fun onStart() {
        super.onStart()
        mGameView.resume()
    }

    override fun onPause() {
        super.onPause()
        mGameView.pause()
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is GameState) {
            if (o.running) {
                runOnUiThread {
                    mGameOverText.visibility = View.GONE
                    mStartButton.visibility = View.GONE
                    highScoreLabel.visibility = View.GONE
                    highScoreValue.visibility = View.GONE
                    mHeadline.visibility = View.GONE
                    version.visibility = View.GONE
                    thanks.visibility = View.GONE
                }
            } else {
                mGameView.gameState.deleteObserver(this)
                runOnUiThread {
                    mGameOverText.visibility = View.VISIBLE
                    val highScore = o.highScore()
                    mGameOverText.text =
                        getString(R.string.your_score, highScore)
                    mStartButton.visibility = View.VISIBLE
                    saveHighScore(highScore)
                    highScoreLabel.visibility = View.VISIBLE
                    highScoreValue.visibility = View.VISIBLE
                    mHeadline.visibility = View.VISIBLE
                    version.visibility = View.VISIBLE
                    thanks.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun saveHighScore(highScore: Long) {
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_highscore_key), Context.MODE_PRIVATE
        )
        val score = sharedPref.getLong("LastHighScore", 0L)
        if(highScore > score){
            sharedPref.edit().putLong("LastHighScore", highScore).apply()
            currentHighScore = highScore
            highScoreLabel.text = getString(R.string.new_high_score)
            highScoreValue.text = currentHighScore.toString()
        }else{
            highScoreLabel.text = getString(R.string.current_highscore)
        }

    }
}