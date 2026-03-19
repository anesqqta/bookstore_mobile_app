package com.kp.projectbookstore.activities

import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val loginInput = binding.etLogin.text.toString().trim()
            val passwordInput = binding.etPassword.text.toString().trim()

            if (loginInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Будь ласка, заповніть усі поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = TestData.currentUser

            if ((loginInput == user.email || loginInput == user.name) && passwordInput == user.password) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}