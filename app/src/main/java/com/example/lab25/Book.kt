package com.example.lab25

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val author: String,
    val status: BookStatus // Новый или Прочитана
)

enum class BookStatus {
    NEW, READ
}