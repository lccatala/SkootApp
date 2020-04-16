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
import kotlinx.android.synthetic.main.activity_change_password.*
import org.json.JSONObject


class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var oldPassword: String
    private lateinit var newPassword: String
    private lateinit var email: String

    private var jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        changePasswordButton.setOnClickListener { changePassword() }
        cancelChangePasswordButton.setOnClickListener { finish() }
    }

    private fun changePassword() {
        oldPassword = oldPasswordText.text.toString()
        newPassword = newPasswordText.text.toString()
        email = changePasswordEmailText.text.toString()

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.backend_url) + "/settings"
        jsonObj.put("Setting", "Password")
        jsonObj.put("Email", email)
        jsonObj.put("Value", newPassword)
        jsonObj.put("Password", oldPassword)
        val jsonRequest = JsonObjectRequest(Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    Toast.makeText(this,"Password updated", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MenuActivity::class.java)
                    intent.putExtra("Fname", response["Fname"].toString())
                    intent.putExtra("Lname", response["Lname"].toString())
                    intent.putExtra("Email", email)
                    intent.putExtra("Password", newPassword)
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
}