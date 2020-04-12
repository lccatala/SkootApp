package com.example.skoot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String

    private var jsonObj = JSONObject()

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

    // Authenticates user with server. If successful, switches to activity_menu
    fun login() {
        email = usernameText.text.toString()
        password = passwordText.text.toString()

        if (!dataIsValid())
            return

        jsonObj.put("Email", email)
        jsonObj.put("Password", password)

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.backend_url) + "/login"
        val jsonRequest = JsonObjectRequest(Request.Method.POST, url, jsonObj,
            Response.Listener { response ->

                if (response["Authorized"] as Boolean) {
                    var intent = Intent(this, MenuActivity::class.java)
                    Log.d("JSONObj", "testing...")

                    intent.putExtra("Fname", response["Fname"].toString())
                    intent.putExtra("Lname", response["Lname"].toString())
                    intent.putExtra("Email", email)
                    intent.putExtra("Phone", response["Phone"].toString())
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Something went wrong: " + error.toString(), Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)

        /*
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
         */
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