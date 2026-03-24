package com.kp.projectbookstore

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kp.projectbookstore.activities.BookDetailsActivity
import com.kp.projectbookstore.activities.FavoritesActivity
import com.kp.projectbookstore.activities.ProfileActivity
import com.kp.projectbookstore.adapters.BookAdapter
import com.kp.projectbookstore.data.TestData
import com.kp.projectbookstore.databinding.ActivityMainBinding
import com.kp.projectbookstore.models.Book
import com.kp.projectbookstore.utils.BookPreferences

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: BookAdapter
    private lateinit var allBooks: List<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BookPreferences.loadBookStates(this)

        allBooks = TestData.books

        setupRecyclerView()
        setupSearch()
        setupBottomNavigation()
    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter(TestData.books) {
            selectedBook ->
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

        binding.rvBooks.layoutManager = LinearLayoutManager(this)
        binding.rvBooks.adapter = bookAdapter
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterBooks(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterBooks(query: String) {
        val filteredBooks = if (query.isBlank()) {
            allBooks
        } else {
            allBooks.filter { book ->
                book.title.contains(query, ignoreCase = true) ||
                        book.author.contains(query, ignoreCase = true) ||
                        book.genre.contains(query, ignoreCase = true)
            }
        }

        bookAdapter.updateBooks(filteredBooks)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_favorites -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

    }
    override fun onResume() {
        super.onResume()
        bookAdapter.updateBooks(allBooks)
    }

}


