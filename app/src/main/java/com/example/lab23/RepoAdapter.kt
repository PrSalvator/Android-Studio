package com.example.lab23
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RepoAdapter(private val repos: List<Repository>) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.repoNameTextView)
        val description: TextView = itemView.findViewById(R.id.repoDescriptionTextView)
        val author: TextView = itemView.findViewById(R.id.repoAuthorTextView)
        val language: TextView = itemView.findViewById(R.id.repoLanguageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos[position]
        holder.name.text = repo.full_name
        holder.description.text = repo.description ?: "No description"
        holder.author.text = "Author: ${repo.owner.login}"
        holder.language.text = "Language: ${repo.language ?: "Unknown"}"

        // Обработка нажатия на элемент списка для открытия ссылки в браузере
        holder.itemView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.html_url))
            holder.itemView.context.startActivity(browserIntent)
        }
    }

    override fun getItemCount() = repos.size
}
