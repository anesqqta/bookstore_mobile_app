package com.kp.projectbookstore

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kp.projectbookstore.activities.BookDetailsActivity
import com.kp.projectbookstore.activities.ProfileActivity
import com.kp.projectbookstore.activities.SearchActivity
import com.kp.projectbookstore.adapters.BookAdapter
import com.kp.projectbookstore.data.TestData
import com.kp.projectbookstore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
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

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
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

}
