package com.example.skoot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.nextButton
import kotlinx.android.synthetic.main.activity_signup.switchToLoginText
import kotlinx.android.synthetic.main.activity_signup2.*
import org.json.JSONObject
import kotlin.math.log

class SignupActivity2 : AppCompatActivity() {
    private lateinit var creditCardNo: String
    private lateinit var cvvCode: String

    private var jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)

        // Hide action bar
        if (supportActionBar != null)
            supportActionBar?.hide()


        signupButton.setOnClickListener {
            signup()
        }

        // Go back
        backButton.setOnClickListener {
            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        // Switch to activity_login
        switchToLoginText.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    // Creates user in server. If successful, switches to activity_menu
    fun signup() {
        creditCardNo = creditCardNoText.text.toString()
        cvvCode = cvvCodeText.text.toString()

        if (!dataIsValid())
            return

        // Get values from activity_signup
        val fname = intent.getStringExtra("Fname")
        val lname = intent.getStringExtra("Lname")
        val email = intent.getStringExtra("Email")
        val password = intent.getStringExtra("Password")

        // Store values to be sent to server
        jsonObj.put("Fname", fname)
        jsonObj.put("Lname", lname)
        jsonObj.put("Email", email)
        jsonObj.put("Password", password)
        jsonObj.put("CreditCardNo", creditCardNo)
        jsonObj.put("CVV", cvvCode)

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.backend_url) + "/signup"
        val jsonRequest = JsonObjectRequest(Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    var intent = Intent(this, MenuActivity::class.java)
                    // Send all values to activity_menu
                    intent.putExtra("Fname", fname)
                    intent.putExtra("Lname", lname)
                    intent.putExtra("Email", email)
                    intent.putExtra("Password", password)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Something went wrong: " + error.toString(), Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }

    fun dataIsValid(): Boolean {
        if (creditCardNo.isEmpty() || cvvCode.isEmpty()) {
            Toast.makeText(this,"Please fill all text fields", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
}
