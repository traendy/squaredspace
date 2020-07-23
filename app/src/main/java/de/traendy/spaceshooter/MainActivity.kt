package de.traendy.spaceshooter

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import de.traendy.spaceshooter.databinding.ActivityMainBinding
import de.traendy.spaceshooter.game.Menu
import de.traendy.spaceshooter.game.StateMediator
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity(), StateMediator.Listener {

    private var currentHighScore: Long = 0L

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        StateMediator.register(this)
        view.startButton.setOnClickListener {
            resetGameState()
        }

        view.shareButton.setOnClickListener{
            share()
        }

        getOldHighScore(view)
        view.version.text = BuildConfig.VERSION_NAME
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

    private fun getOldHighScore(view: ConstraintLayout) {
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_highscore_key), Context.MODE_PRIVATE
        )
        currentHighScore = sharedPref.getLong("LastHighScore", 0L)
        view.highScoreLabel.text = getString(R.string.current_highscore)
        view.highScoreValue.text = currentHighScore.toString()
    }

    private fun showMenu() {
        binding.root.highScoreLabel.visibility = View.VISIBLE
        binding.root.highScoreValue.visibility = View.VISIBLE
        binding.root.headline.visibility = View.VISIBLE
        binding.root.version.visibility = View.VISIBLE
        binding.root.specialThanks.visibility = View.VISIBLE
        binding.root.startButton.visibility = View.VISIBLE
//        binding.root.shareButton.visibility = View.VISIBLE
    }

    private fun resetGameState() {
//        binding.root.gameView.gameState.addObserver(this)
        binding.root.gameView.gameState.reset()
        StateMediator.progressState()
    }

    override fun onStart() {
        super.onStart()
        binding.root.gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.root.gameView.pause()
    }

    private fun updateHighScore(highScore: Long) {
        binding.root.gameOverText.visibility = View.VISIBLE
        binding.root.gameOverText.text =
            getString(R.string.your_score, highScore)
        saveHighScore(highScore)
    }

    private fun hideMenu() {
        binding.root.gameOverText.visibility = View.GONE
        binding.root.startButton.visibility = View.GONE
        binding.root.highScoreLabel.visibility = View.GONE
        binding.root.highScoreValue.visibility = View.GONE
        binding.root.headline.visibility = View.GONE
        binding.root.version.visibility = View.GONE
        binding.root.shareButton.visibility = View.GONE
        binding.root.specialThanks.visibility = View.GONE
    }

    private fun saveHighScore(highScore: Long) {
        val sharedPref = getSharedPreferences(
            getString(R.string.preference_highscore_key), Context.MODE_PRIVATE
        )
        val score = sharedPref.getLong("LastHighScore", 0L)
        if (highScore > score) {
            sharedPref.edit().putLong("LastHighScore", highScore).apply()
            currentHighScore = highScore
            binding.root.highScoreLabel.text = getString(R.string.new_high_score)
            binding.root.highScoreValue.text = currentHighScore.toString()
        } else {
            binding.root.highScoreLabel.text = getString(R.string.current_highscore)
        }

    }

    override fun onStateChange() {
        runOnUiThread {
            when(StateMediator.getState()){
                is Menu -> {
                    showMenu()
                    updateHighScore(binding.root.gameView.gameState.highScore())
                }
                else -> hideMenu()
            }
        }
    }
}