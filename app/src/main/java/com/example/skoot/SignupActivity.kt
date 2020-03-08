package com.example.skoot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Hide action bar
        if (supportActionBar != null)
            supportActionBar?.hide()


        signupButton.setOnClickListener {
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()
            val repeatedPassword = repeatPasswordText.text.toString()

            if (username.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
                Toast.makeText(this,"Please enter both username and password, and remember to confirm your password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (password == repeatedPassword) {
                var intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("EXTRA_USERNAME", username)
                startActivity(intent)
            } else {
                Log.d("Debugging", "Passwords  $password and $repeatedPassword are not the same!")
            }
        }

        // Switch to activity_main
        switchToLoginText.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
