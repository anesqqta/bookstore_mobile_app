package com.kp.projectbookstore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kp.projectbookstore.databinding.ItemBookBinding
import com.kp.projectbookstore.models.Book

class BookAdapter(
    private var books: List<Book>,
    private val onBookClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Book) {
            binding.ivBookImage.setImageResource(book.imageResId)
            binding.tvBookTitle.text = book.title
            binding.tvBookAuthor.text = book.author
            binding.tvBookGenre.text = book.genre
            binding.tvBookStatus.text = if (book.inStock) "В наявності" else "Немає в наявності"
            binding.tvBookPrice.text = "${book.price} грн"

            binding.tvBookStatus.setTextColor(
                if (book.inStock)
                    binding.root.context.getColor(android.R.color.holo_green_dark)
                else
                    binding.root.context.getColor(android.R.color.holo_red_dark)
            )

            binding.root.setOnClickListener {
                onBookClick(book)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size

    fun updateBooks(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}

