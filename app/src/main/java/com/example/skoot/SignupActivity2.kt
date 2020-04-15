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
    private lateinit var fname: String
    private lateinit var lname: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var creditCardNo: String
    private lateinit var creditCardCVV: String

    private var jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)

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
        // Get values from activity_signup
        fname = intent.getStringExtra("Fname")
        lname = intent.getStringExtra("Lname")
        email = intent.getStringExtra("Email")
        password = intent.getStringExtra("Password")

        // Get values from activity_signup2
        creditCardNo = creditCardNoText.text.toString()
        creditCardCVV = cvvCodeText.text.toString()

        if (!dataIsValid())
            return

        // Store values to be sent to server
        jsonObj.put("Fname", fname)
        jsonObj.put("Lname", lname)
        jsonObj.put("Email", email)
        jsonObj.put("Password", password)
        jsonObj.put("CreditCardNo", creditCardNo)
        jsonObj.put("CVV", creditCardCVV)


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
        if (fname.isEmpty() || lname.isEmpty()) {
            Toast.makeText(this,"Please fill all text fields", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
}
