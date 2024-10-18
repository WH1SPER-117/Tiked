package com.example.ticked

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ticked.R

class login : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        emailEditText = findViewById(R.id.emailEditText) // Add this EditText in XML
        passwordEditText = findViewById(R.id.passwordEditText) // Add this EditText in XML

        val btnNavigate: TextView = findViewById(R.id.btnsignin)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }

        // Login button logic
        val logbtnNavigate: Button = findViewById(R.id.btnlogin)
        logbtnNavigate.setOnClickListener {
            Log.d("LoginActivity", "Login button clicked")
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateCredentials(email, password)) {
                try {
                    Log.d("LoginActivity", "Starting Homepage activity")
                    val intent = Intent(this, homepage::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("LoginActivity", "Error starting homepage activity", e)
                }
            } else {
                Log.d("LoginActivity", "Invalid credentials")
                // You can show a Toast or Snackbar here to notify the user
            }
        }
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        return email == "hkenula@gmail.com" && password == "12345"
    }
}
