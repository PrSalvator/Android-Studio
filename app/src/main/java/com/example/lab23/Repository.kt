package com.example.lab23

data class Repository(
    val id: Long,
    val name: String,
    val full_name: String,
    val html_url: String,
    val description: String?,
    val owner: Owner,
    val language: String?
)

data class Owner(
    val login: String
)
