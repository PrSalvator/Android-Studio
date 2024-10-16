package com.example.lab25

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(
    private val books: List<Book>,
    private val onStatusChange: (Book) -> Unit,
    private val onEditBook: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val statusButton: Button = itemView.findViewById(R.id.statusButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.titleTextView.text = "Название: ${book.title}"
        holder.authorTextView.text = "Автор: ${book.author}"
        holder.statusButton.text = if (book.status == BookStatus.NEW) "Mark as Read" else "Mark as New"

        holder.statusButton.setOnClickListener {
            val newStatus = if (book.status == BookStatus.NEW) BookStatus.READ else BookStatus.NEW
            onStatusChange(book.copy(status = newStatus))
        }

        holder.statusButton.setOnClickListener {
            val newStatus = if (book.status == BookStatus.NEW) BookStatus.READ else BookStatus.NEW
            onStatusChange(book.copy(status = newStatus))
        }

        // Обработка клика по элементу для редактирования
        holder.itemView.setOnClickListener {
            onEditBook(book)
        }
    }



    override fun getItemCount(): Int = books.size
}