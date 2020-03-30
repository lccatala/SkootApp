package com.example.skoot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_signup.*
import kotlin.math.log

class SignupActivity : AppCompatActivity() {
    private var email: String = ""
    private var password: String = ""
    private var repeatedPassword: String = ""
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        // Hide action bar
        if (supportActionBar != null)
            supportActionBar?.hide()


        signupButton.setOnClickListener {
            signup()
        }

        // Switch to activity_login
        switchToLoginText.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Creates user in Firebase. If successful, switches to activity_menu
    fun signup() {
        email = usernameText.text.toString()
        password = passwordText.text.toString()
        repeatedPassword = repeatPasswordText.text.toString()

        if (!dataIsValid())
            return

        auth.createUserWithEmailAndPassword(email, password)
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
        if (email.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty()) {
            Toast.makeText(this,"Please enter both username and password, and remember to confirm your password", Toast.LENGTH_LONG).show()
            return false
        }

        if (password != repeatedPassword) {
            Toast.makeText(this,"Passwords don't match", Toast.LENGTH_LONG).show()
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
