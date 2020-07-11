package de.traendy.spaceshooter

import android.content.Context
import android.content.pm.ActivityInfo
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.button.MaterialButton
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import de.traendy.spaceshooter.game.GameState
import de.traendy.spaceshooter.game.GameView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), Observer {

    private lateinit var mGameView: GameView
    private lateinit var mGameOverText: TextView
    private lateinit var mHighScoreLabel: TextView
    private lateinit var mHighScoreValue: TextView
    private lateinit var mStartButton: MaterialButton
    private var currentHighScore: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mGameView = findViewById(R.id.gameView)
        mGameOverText = findViewById(R.id.gameOverText)
        mHighScoreLabel = findViewById(R.id.highScoreLabel)
        mHighScoreValue = findViewById(R.id.highScoreValue)
        mStartButton = findViewById(R.id.startButton)
        mStartButton.setOnClickListener {
            resetGameState()
        }
        mGameView.gameState.addObserver(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_highscore_key), Context.MODE_PRIVATE
        )
        currentHighScore = sharedPref.getLong("LastHighScore", 0L)
        highScoreLabel.text = "Current Highscore:"
        highScoreValue.text = currentHighScore.toString()
        highScoreLabel.visibility = View.VISIBLE
        highScoreValue.visibility = View.VISIBLE
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
                }
            } else {
                mGameView.gameState.deleteObserver(this)
                runOnUiThread {
                    mGameOverText.visibility = View.VISIBLE
                    val highscore = o.highScore()
                    mGameOverText.text = " Your score: $highscore."
                    mStartButton.visibility = View.VISIBLE
                    saveHighScore(highscore)
                    highScoreLabel.visibility = View.VISIBLE
                    highScoreValue.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun saveHighScore(highscore: Long) {
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_highscore_key), Context.MODE_PRIVATE
        )
        val score = sharedPref.getLong("LastHighScore", 0L)
        if(highscore > score){
            sharedPref.edit().putLong("LastHighScore", highscore).apply()
            currentHighScore = highscore
            highScoreLabel.text = "New Highscore:"
            highScoreValue.text = currentHighScore.toString()
        }else{
            highScoreLabel.text = "Current Highscore:"
        }

    }
}