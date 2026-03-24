package com.kp.projectbookstore.utils

import android.content.Context
import com.kp.projectbookstore.data.TestData

object BookPreferences {

    private const val PREF_NAME = "book_states"

    fun saveBookStates(context: Context) {
        val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()

        TestData.books.forEach { book ->
            editor.putBoolean("favorite_${book.id}", book.isFavorite)
            editor.putBoolean("cart_${book.id}", book.isInCart)
            editor.putBoolean("stock_${book.id}", book.inStock)
        }

        editor.apply()
    }

    fun loadBookStates(context: Context) {
        val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        TestData.books.forEach { book ->
            book.isFavorite = preferences.getBoolean("favorite_${book.id}", book.isFavorite)
            book.isInCart = preferences.getBoolean("cart_${book.id}", book.isInCart)
            book.inStock = preferences.getBoolean("stock_${book.id}", book.inStock)
        }
    }
}