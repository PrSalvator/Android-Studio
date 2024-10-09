package com.example.catandmousegame
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreAdapter(private val scores: List<GameResult>) :
    RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    // ViewHolder, который будет держать ссылки на виджеты для каждого элемента списка
    class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameTimeText: TextView = itemView.findViewById(R.id.game_time_text)
        val clicksText: TextView = itemView.findViewById(R.id.clicks_text)
        val hitsText: TextView = itemView.findViewById(R.id.hits_text)
        val accuracyText: TextView = itemView.findViewById(R.id.accuracy_text)
    }

    // Метод для создания нового ViewHolder, когда RecyclerView его запрашивает
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        // Инфлейтим XML-разметку для каждого элемента списка
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    // Метод для связывания данных с ViewHolder
    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        val score = scores[position]
        holder.gameTimeText.text = "Время игры: ${score.time}"
        holder.clicksText.text = "Всего кликов: ${score.clicks}"
        holder.hitsText.text = "Попадания по мышке: ${score.hits}"
        holder.accuracyText.text = "Точность: ${score.accuracy}%"
    }

    // Метод для получения количества элементов в списке
    override fun getItemCount(): Int {
        return scores.size
    }
}
