package com.example.catandmousegame

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catandmousegame.databinding.ActivityScoreBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScoreActivity : AppCompatActivity() {
    val context = this
    private lateinit var scoreRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        scoreRecyclerView = findViewById(R.id.score_recycler_view)

        // Получение данных из БД

        GlobalScope.launch {
            val db = GameDatabase.getInstance(context).gameDao()
            val results = db.getLast10Results()
            displayResults(results)
        }
    }

    private fun displayResults(results: List<GameResult>) {
        scoreRecyclerView.adapter = ScoreAdapter(results)
        scoreRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}