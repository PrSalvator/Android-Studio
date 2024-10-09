package com.example.catandmousegame

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private lateinit var gameScoreText: TextView
    private var screenWidth = 0
    private var screenHeight = 0
    private var clickCount: Int = 0
    private var hitCount: Int = 0
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("ClickableViewAccessibility", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_game)
        super.onCreate(savedInstanceState)

        gameScoreText = findViewById(R.id.game_score_text)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        // Получение параметров из MainActivity
        val mouseCount = intent.getIntExtra("MOUSE_COUNT", 1)
        val speed = intent.getIntExtra("SPEED", 5)
        val size = intent.getIntExtra("SIZE", 50)
        // Инициализация игровой области
        gameView = GameView(this, mouseCount, speed, size, screenWidth, screenHeight)

        val frameLayout = findViewById<FrameLayout>(R.id.frame_layout)
        frameLayout.addView(gameView, 0)

        gameView.setOnTouchListener { _, event ->
            gameView.onTouchEvent(event)
            updateValues()
            true
        }

    }

    private fun updateValues(){
        clickCount = gameView.getTotalClicks()
        hitCount = gameView.getHits()
        updateScoreText()
    }

    private fun updateScoreText() {
        gameScoreText.text = "Клики: $clickCount Попадания: $hitCount"
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Остановка обновлений при разрушении активности
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        // Закрытие игры по нажатию кнопки "Back"
        super.onBackPressed()
        gameView.saveResults()
    }
}