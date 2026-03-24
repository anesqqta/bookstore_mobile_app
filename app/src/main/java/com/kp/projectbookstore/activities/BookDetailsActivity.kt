package com.kp.projectbookstore.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kp.projectbookstore.R
import com.kp.projectbookstore.data.TestData
import com.kp.projectbookstore.databinding.ActivityBookDetailsBinding

class BookDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title") ?: "Unknown title"
        val author = intent.getStringExtra("author") ?: "Unknown author"
        val genre = intent.getStringExtra("genre") ?: "Unknown genre"
        val price = intent.getDoubleExtra("price", 0.0)
        val description = intent.getStringExtra("description") ?: "No description"
        val imageResId = intent.getIntExtra("imageResId", 0)

        binding.tvBookTitle.text = title
        binding.tvBookAuthor.text = author
        binding.tvBookGenre.text = genre
        binding.tvBookPrice.text = "$price грн"
        binding.tvBookDescription.text = description

        if (imageResId != 0) {
            binding.ivBookImage.setImageResource(imageResId)
        }

        val selectedBook = TestData.books.find { it.title == title && it.author == author }

        updateFavoriteButton(selectedBook?.isFavorite == true)

        binding.btnAddToFavorites.setOnClickListener {
            selectedBook?.let { book ->
                book.isFavorite = !book.isFavorite
                updateFavoriteButton(book.isFavorite)

                val message = if (book.isFavorite) {
                    "Книгу додано в обране"
                } else {
                    "Книгу видалено з обраного"
                }

                Toast.makeText(this, "$title added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateFavoriteButton(isFavorite: Boolean) {
        binding.btnAddToFavorites.text =
            if (isFavorite) "Видалити з обраного" else "Додати в обране"
    }
}