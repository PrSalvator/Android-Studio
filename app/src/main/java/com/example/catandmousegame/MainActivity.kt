package com.example.catandmousegame

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SeekBar
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var scoreButton: Button
    private lateinit var mouseCountSpinner: Spinner
    private lateinit var speedSeekBar: SeekBar
    private lateinit var sizeSeekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton = findViewById(R.id.start_button)
        scoreButton = findViewById(R.id.score_button)
        mouseCountSpinner = findViewById(R.id.mouse_count_spinner)
        speedSeekBar = findViewById(R.id.speed_seekbar)
        sizeSeekBar = findViewById(R.id.size_seekbar)

        val mouseCounts = arrayOf(1, 2, 3, 4, 5)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mouseCounts)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mouseCountSpinner.adapter = adapter

        // Обработчик для кнопки "Старт"
        startButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("MOUSE_COUNT", mouseCountSpinner.selectedItem.toString().toInt())
            intent.putExtra("SPEED", speedSeekBar.progress)
            intent.putExtra("SIZE", sizeSeekBar.progress)
            startActivity(intent)
        }

        // Переход к экрану со счетом
        scoreButton.setOnClickListener {
            startActivity(Intent(this, ScoreActivity::class.java))
        }
    }
}
