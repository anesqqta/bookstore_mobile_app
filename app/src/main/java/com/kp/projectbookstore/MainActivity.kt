package com.kp.projectbookstore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
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

        setupRecyclerBiew()
    }

    private fun setupRecyclerBiew() {
        bookAdapter = BookAdapter(TestData.books) {
            selectedBook -> Toast.makeText(this, "Selected: ${selectedBook.title}", Toast.LENGTH_SHORT).show()
        }

        binding.rvBooks.layoutManager = LinearLayoutManager(this)
        binding.rvBooks.adapter = bookAdapter
    }
}