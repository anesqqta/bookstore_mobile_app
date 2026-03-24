package com.kp.projectbookstore.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kp.projectbookstore.MainActivity
import com.kp.projectbookstore.R
import com.kp.projectbookstore.data.TestData
import com.kp.projectbookstore.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = getSharedPreferences("bookstore_prefs", MODE_PRIVATE)

        if (preferences.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val loginInput = binding.etLogin.text.toString().trim()
            val passwordInput = binding.etPassword.text.toString().trim()

            if (loginInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Будь ласка, заповніть усі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = TestData.users.find {
                (loginInput == it.email || loginInput == it.name) &&
                        passwordInput == it.password
            }

            if (user != null) {
                preferences.edit()
                    .putBoolean("isLoggedIn", true)
                    .putString("userName", user.name)
                    .putString("userEmail", user.email)
                    .apply()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }

}