package com.kp.projectbookstore.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kp.projectbookstore.R
import com.kp.projectbookstore.data.TestData
import com.kp.projectbookstore.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("bookstore_prefs", MODE_PRIVATE)

        val userName = preferences.getString("userName", "Unknown user")
        val userEmail = preferences.getString("userEmail", "No email")

        binding.tvUserName.text = userName
        binding.tvUserEmail.text = userEmail

        binding.btnOpenFavorites.setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        binding.btnOpenCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            preferences.edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}