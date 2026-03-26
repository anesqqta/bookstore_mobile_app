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
import com.kp.projectbookstore.utils.BookPreferences

class BookDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BookPreferences.loadBookStates(this)

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
        updateCartButton(selectedBook?.isInCart == true)
        updateStockButton(selectedBook?.inStock == true)

        binding.btnAddToFavorites.setOnClickListener {
            selectedBook?.let { book ->
                book.isFavorite = !book.isFavorite
                BookPreferences.saveBookStates(this)
                updateFavoriteButton(book.isFavorite)

                val message = if (book.isFavorite) {
                    "Книгу додано в обране"
                } else {
                    "Книгу видалено з обраного"
                }

                Toast.makeText(this, "$title added to favorites", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnAddToCart.setOnClickListener {
            selectedBook?.let { book ->
                if (!book.inStock) {
                    Toast.makeText(
                        this,
                        "Книги немає в наявності, зачекайте поки з'явиться в наявності",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@let
                }

                book.isInCart = !book.isInCart
                BookPreferences.saveBookStates(this)
                updateCartButton(book.isInCart)

                val message = if (book.isInCart) {
                    "Книгу додано в кошик"
                } else {
                    "Книгу видалено з кошика"
                }

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnToggleStock.setOnClickListener {
            selectedBook?.let { book ->
                book.inStock = !book.inStock
                BookPreferences.saveBookStates(this)
                updateStockButton(book.inStock)

                val message = if (book.inStock) {
                    "Книга позначена як в наявності"
                } else {
                    "Книга позначена як немає в наявності"
                }

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateFavoriteButton(isFavorite: Boolean) {
        binding.btnAddToFavorites.text =
            if (isFavorite) "Видалити з обраного" else "Додати в обране"
    }
    private fun updateCartButton(isInCart: Boolean) {
        binding.btnAddToCart.text =
            if (isInCart) "Видалити з кошика" else "Додати в кошик"
    }
    private fun updateStockButton(inStock: Boolean) {
        binding.btnToggleStock.text =
            if (inStock) "Позначити як немає в наявності" else "Позначити як в наявності"
    }
}