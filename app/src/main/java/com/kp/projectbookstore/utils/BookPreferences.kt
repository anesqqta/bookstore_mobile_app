package com.kp.projectbookstore.utils

import android.content.Context
import com.kp.projectbookstore.data.TestData

object BookPreferences {

    private const val PREF_NAME = "book_states"
    private const val USER_PREF_NAME = "bookstore_prefs"

    private fun getCurrentUserId(context: Context): Int {
        val userPrefs = context.getSharedPreferences(USER_PREF_NAME, Context.MODE_PRIVATE)
        return userPrefs.getInt("userId", 0)
    }
    fun saveBookStates(context: Context) {
        val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val userId = getCurrentUserId(context)

        TestData.books.forEach { book ->
            editor.putBoolean("favorite_user_${userId}_book_${book.id}", book.isFavorite)
            editor.putBoolean("cart_user_${userId}_book_${book.id}", book.isInCart)
            editor.putBoolean("stock_${book.id}", book.inStock)
        }
        editor.apply()
    }
    fun loadBookStates(context: Context) {
        val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userId = getCurrentUserId(context)

        TestData.books.forEach { book ->
            book.isFavorite = preferences.getBoolean("favorite_user_${userId}_book_${book.id}", false)
            book.isInCart = preferences.getBoolean("cart_user_${userId}_book_${book.id}", false)
            book.inStock = preferences.getBoolean("stock_${book.id}", book.inStock)
        }
    }
}