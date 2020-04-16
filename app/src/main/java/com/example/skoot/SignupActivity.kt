package com.example.skoot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    private lateinit var fname: String
    private lateinit var lname: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var repeatedPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        cancelSignupButton.setOnClickListener { finish() }

        nextButton.setOnClickListener { next() }

        // Switch to activity_login
        switchToLoginText.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Creates user in server. If successful, switches to activity_menu
    fun next() {
        fname = fnameText.text.toString()
        lname = lnameText.text.toString()
        email = emailText.text.toString()
        password = passwordText.text.toString()
        repeatedPassword = repeatPasswordText.text.toString()

        if (!dataIsValid())
            return

        var signupIntent2 = Intent(this, SignupActivity2::class.java)
        signupIntent2.putExtra("Fname", fname)
        signupIntent2.putExtra("Lname", lname)
        signupIntent2.putExtra("Email", email)
        signupIntent2.putExtra("Password", password)
        startActivity(signupIntent2)
    }

    fun dataIsValid(): Boolean {
        if (email.isEmpty() || password.isEmpty() || repeatedPassword.isEmpty() || fname.isEmpty() || lname.isEmpty()) {
            Toast.makeText(this,"Please fill all text fields", Toast.LENGTH_LONG).show()
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
