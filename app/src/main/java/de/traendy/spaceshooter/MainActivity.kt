package de.traendy.spaceshooter

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import de.traendy.spaceshooter.databinding.ActivityMainBinding
import de.traendy.spaceshooter.game.Menu
import de.traendy.spaceshooter.game.StateMediator


class MainActivity : AppCompatActivity(), StateMediator.Listener {

    private var currentHighScore: Long = 0L

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        StateMediator.register(this)
        binding.startButton.setOnClickListener {
            resetGameState()
        }

        binding.shareButton.setOnClickListener{
            share()
        }

        getOldHighScore()
        binding.version.text = BuildConfig.VERSION_NAME
        showMenu()
    }

    private fun share() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody = "Check out my Highscore playing Squared Space: ${binding.highScoreValue.text} Points! :-)"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Squared Space")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    private fun getOldHighScore() {
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_highscore_key), Context.MODE_PRIVATE
        )
        currentHighScore = sharedPref.getLong("LastHighScore", 0L)
        binding.highScoreLabel.text = getString(R.string.current_highscore)
        binding.highScoreValue.text = currentHighScore.toString()
    }

    private fun showMenu() {
        binding.highScoreLabel.visibility = View.VISIBLE
        binding.highScoreValue.visibility = View.VISIBLE
        binding.headline.visibility = View.VISIBLE
        binding.version.visibility = View.VISIBLE
        binding.specialThanks.visibility = View.VISIBLE
        binding.startButton.visibility = View.VISIBLE
//        binding.shareButton.visibility = View.VISIBLE
    }

    private fun resetGameState() {
//        binding.gameView.gameState.addObserver(this)
        binding.gameView.gameState.reset()
        StateMediator.progressState()
    }

    override fun onStart() {
        super.onStart()
        binding.gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.gameView.pause()
    }

    private fun updateHighScore(highScore: Long) {
        binding.gameOverText.visibility = View.VISIBLE
        binding.gameOverText.text =
            getString(R.string.your_score, highScore)
        saveHighScore(highScore)
    }

    private fun hideMenu() {
        binding.gameOverText.visibility = View.GONE
        binding.startButton.visibility = View.GONE
        binding.highScoreLabel.visibility = View.GONE
        binding.highScoreValue.visibility = View.GONE
        binding.headline.visibility = View.GONE
        binding.version.visibility = View.GONE
        binding.shareButton.visibility = View.GONE
        binding.specialThanks.visibility = View.GONE
    }

    private fun saveHighScore(highScore: Long) {
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_highscore_key), Context.MODE_PRIVATE
        )
        val score = sharedPref.getLong("LastHighScore", 0L)
        if (highScore > score) {
            sharedPref.edit().putLong("LastHighScore", highScore).apply()
            currentHighScore = highScore
            binding.highScoreLabel.text = getString(R.string.new_high_score)
            binding.highScoreValue.text = currentHighScore.toString()
        } else {
            binding.highScoreLabel.text = getString(R.string.current_highscore)
        }

    }

    override fun onStateChange() {
        runOnUiThread {
            when(StateMediator.getState()){
                is Menu -> {
                    showMenu()
                    updateHighScore(binding.gameView.gameState.highScore())
                }
                else -> hideMenu()
            }
        }
    }
}