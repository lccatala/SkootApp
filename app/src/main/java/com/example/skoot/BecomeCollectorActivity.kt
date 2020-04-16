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
import kotlinx.android.synthetic.main.activity_become_collector.*
import org.json.JSONObject


class BecomeCollectorActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String

    private var jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_become_collector)

        becomeCollectorButton.setOnClickListener { becomeCollector("true") }
        becomeCollectorStopButton.setOnClickListener { becomeCollector("false") }
        cancelBecomeCollectorButton.setOnClickListener { finish() }
    }

    private fun becomeCollector(value: String) {
        val letter = becomeCollectorLetterText.text.toString()
        password = becomeCollectorPasswordText.text.toString()
        email = becomeCollectorEmailText.text.toString()

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.backend_url) + "/settings"
        jsonObj.put("Email", email)
        jsonObj.put("Password", password)
        jsonObj.put("Setting", "Collector")
        jsonObj.put("Value", "$value|@|$letter")
        val jsonRequest = JsonObjectRequest(Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    Toast.makeText(this,"Updated Collector Settings", Toast.LENGTH_LONG).show()
                    var intent = Intent(this, MenuActivity::class.java)
                    intent.putExtra("Fname", response["Fname"].toString())
                    intent.putExtra("Lname", response["Lname"].toString())
                    intent.putExtra("Email", email)
                    intent.putExtra("Password", password)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"Incorrect password", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"Something went wrong: $error", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }
}