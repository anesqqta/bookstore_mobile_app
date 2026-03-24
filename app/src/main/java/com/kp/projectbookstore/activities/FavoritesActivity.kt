package com.kp.projectbookstore.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kp.projectbookstore.activities.BookDetailsActivity
import com.kp.projectbookstore.adapters.BookAdapter
import com.kp.projectbookstore.data.TestData
import com.kp.projectbookstore.databinding.ActivityFavoritesBinding
import com.kp.projectbookstore.utils.BookPreferences

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private lateinit var adapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BookPreferences.loadBookStates(this)

        adapter = BookAdapter(emptyList()) { selectedBook ->
            val intent = Intent(this, BookDetailsActivity::class.java).apply {
                putExtra("title", selectedBook.title)
                putExtra("author", selectedBook.author)
                putExtra("genre", selectedBook.genre)
                putExtra("price", selectedBook.price)
                putExtra("description", selectedBook.description)
                putExtra("imageResId", selectedBook.imageResId)
            }
            startActivity(intent)
        }

        binding.rvFavorites.layoutManager = LinearLayoutManager(this)
        binding.rvFavorites.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val favoriteBooks = TestData.books.filter { it.isFavorite }
        adapter.updateBooks(favoriteBooks)
    }
}
