package com.example.skoot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Hide action bar
        if (supportActionBar != null)
            supportActionBar?.hide()

        // Switch to activity_main
        switchToRegisterText.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Log in
        loginButton.setOnClickListener {
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()

            Log.d("Debugging", "Tried to log in with username $username and password $password")
        }
    }
}