package com.kp.projectbookstore.models

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val genre: String,
    val price: Double,
    val description: String,
    val imageResId: Int,
    var isFavorite: Boolean = false,
    var isInCart: Boolean = false,
    var inStock: Boolean = true
)
