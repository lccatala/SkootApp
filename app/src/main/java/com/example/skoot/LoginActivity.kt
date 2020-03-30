package com.example.skoot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var email: String = ""
    private var password: String = ""
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Hide action bar
        if (supportActionBar != null)
            supportActionBar?.hide()


        // Log in
        loginButton.setOnClickListener {
            login()
        }

        // Switch to activity_signup
        switchToRegisterText.setOnClickListener {
            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    // Authenticates user with Firebase. If successful, switches to activity_menu
    fun login() {
        email = usernameText.text.toString()
        password = passwordText.text.toString()

        if (!dataIsValid())
            return

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var intent = Intent(this, MenuActivity::class.java)
                    intent.putExtra("EXTRA_EMAIL", email)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Could not create user.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun dataIsValid(): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,"Please enter both username and password, and password confirmation",
                Toast.LENGTH_LONG).show()
            return false
        }

        // TODO: check max length
        // Check password length
        if (password.length < 10) {
            Toast.makeText(baseContext, "Passwords must be at least 10 characters long.",
                Toast.LENGTH_LONG).show()
            return false
        }

        // Check email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(baseContext, "Please enter a valid email address.",
                Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
}