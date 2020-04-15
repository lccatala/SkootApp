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
import kotlinx.android.synthetic.main.activity_change_name.*
import org.json.JSONObject


class ChangeNameActivity : AppCompatActivity() {
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var password: String

    private var jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_name)

        changeNameButton.setOnClickListener { changeName() }
    }

    fun changeName() {
        firstName = firstNameText.text.toString()
        lastName = lastNameText.text.toString()
        email = changeNameEmailText.text.toString()
        password = changeNamePasswordText.text.toString()

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.backend_url) + "/settings"
        jsonObj.put("Setting", "Name")
        jsonObj.put("Value", "$firstName|$lastName")
        jsonObj.put("Email", email)
        jsonObj.put("Password", password)
        val jsonRequest = JsonObjectRequest(Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    Toast.makeText(this,"Name updated", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MenuActivity::class.java)
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