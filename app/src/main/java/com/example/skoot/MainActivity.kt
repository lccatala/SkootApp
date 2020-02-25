package com.example.skoot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.passwordText
import kotlinx.android.synthetic.main.activity_main.usernameText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide action bar
        if (supportActionBar != null)
            supportActionBar?.hide()


        registerButton.setOnClickListener {
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()
            val repeatedPassword = repeatPasswordText.text.toString()

            if (password == repeatedPassword) {
                Log.d("Debugging", "Tried to register with username $username and password $password")
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
