package com.example.skoot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    private var email: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        email = intent.getStringExtra("EXTRA_EMAIL")
        welcomeMessage.text = "Welcome, $email!"
    }
}
