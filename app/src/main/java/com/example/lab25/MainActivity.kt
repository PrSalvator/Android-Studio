package com.example.lab25

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var bookViewModel: BookViewModel
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewNewBooks: RecyclerView = findViewById(R.id.recyclerViewNewBooks)
        recyclerViewNewBooks.layoutManager = LinearLayoutManager(this)

        // Настройка RecyclerView для прочитанных книг
        val recyclerViewReadBooks: RecyclerView = findViewById(R.id.recyclerViewReadBooks)
        recyclerViewReadBooks.layoutManager = LinearLayoutManager(this)

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        bookViewModel.newBooks.observe(this, Observer { books ->
            adapter = BookAdapter(books, { book ->
                bookViewModel.update(book)
            }, { book ->
                showAddEditBookDialog(book)
            })
            recyclerViewNewBooks.adapter = adapter
        })

        bookViewModel.readBooks.observe(this, {books ->
            adapter = BookAdapter(books, { book ->
                bookViewModel.update(book)
            }, { book ->
                showAddEditBookDialog(book)
            })
            recyclerViewReadBooks.adapter = adapter
        })

        val fabAddBook: FloatingActionButton = findViewById(R.id.fabAddBook)
        fabAddBook.setOnClickListener {
            showAddEditBookDialog()
        }

        // Аналогично для прочитанных книг
    }

    private fun showAddEditBookDialog(book: Book? = null) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_book, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)

        val dialog = builder.create()

        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val authorEditText = dialogView.findViewById<EditText>(R.id.editTextAuthor)
        val saveButton = dialogView.findViewById<Button>(R.id.buttonSave)
        val deleteButton = dialogView.findViewById<Button>(R.id.buttonDelete)

        // Если редактируем существующую книгу, заполняем поля
        if (book != null) {
            titleEditText.setText(book.title)
            authorEditText.setText(book.author)
            deleteButton.visibility = View.VISIBLE // Показываем кнопку удаления для редактирования
        } else {
            deleteButton.visibility = View.GONE // Скрываем кнопку удаления при добавлении новой книги
        }

        // Сохранение изменений книги
        saveButton.setOnClickListener {
            val updatedBook = book?.copy(
                title = titleEditText.text.toString(),
                author = authorEditText.text.toString()
            ) ?: Book(
                title = titleEditText.text.toString(),
                author = authorEditText.text.toString(),
                status = BookStatus.NEW // Книга добавляется как новая
            )

            if (book == null) {
                bookViewModel.insert(updatedBook)
            } else {
                bookViewModel.update(updatedBook)
            }

            dialog.dismiss()
        }

        // Удаление книги
        deleteButton.setOnClickListener {
            book?.let {
                bookViewModel.delete(it)
            }
            dialog.dismiss()
        }

        dialog.show()
    }
}

