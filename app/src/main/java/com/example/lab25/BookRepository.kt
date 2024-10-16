package com.example.lab25

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    val newBooks: LiveData<List<Book>> = bookDao.getBooksByStatus(BookStatus.NEW)
    val readBooks: LiveData<List<Book>> = bookDao.getBooksByStatus(BookStatus.READ)

    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }

    suspend fun update(book: Book) {
        bookDao.update(book)
    }

    suspend fun delete(book: Book) {
        bookDao.delete(book)
    }
}