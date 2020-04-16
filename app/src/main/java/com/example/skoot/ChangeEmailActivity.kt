package com.example.skoot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_change_email.*
import org.json.JSONObject


class ChangeEmailActivity : AppCompatActivity() {
    private lateinit var oldEmail: String
    private lateinit var newEmail: String
    private lateinit var password: String

    private var jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        changeEmailButton.setOnClickListener { changeEmail() }
        cancelChangeEmailButton.setOnClickListener { finish() }
    }

    fun changeEmail() {
        oldEmail = oldEmailText.text.toString()
        newEmail = newEmailText.text.toString()
        password = changeEmailPasswordText.text.toString()

        if (!dataIsValid())
            return

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.backend_url) + "/settings"
        jsonObj.put("Setting", "Email")
        jsonObj.put("Email", oldEmail)
        jsonObj.put("Value", newEmail)
        jsonObj.put("Password", password)
        val jsonRequest = JsonObjectRequest(Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    Toast.makeText(this,"Email updated", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MenuActivity::class.java)
                    intent.putExtra("Fname", response["Fname"].toString())
                    intent.putExtra("Lname", response["Lname"].toString())
                    intent.putExtra("Email", newEmail)
                    intent.putExtra("Password", password)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"Incorrect password", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Something went wrong: " + error.toString(), Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }

    fun dataIsValid(): Boolean {
        // Check email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()
            || !android.util.Patterns.EMAIL_ADDRESS.matcher(oldEmail).matches()) {
            Toast.makeText(baseContext, "Please enter a valid email address.",
                Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}