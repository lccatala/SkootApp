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
import kotlinx.android.synthetic.main.activity_change_credit_card.*
import kotlinx.android.synthetic.main.activity_signup2.*
import org.json.JSONObject


class ChangeCreditCardActivity : AppCompatActivity() {
    private lateinit var creditCardNo: String
    private lateinit var cvvCode: String
    private lateinit var email: String
    private lateinit var password: String

    private var jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_credit_card)

        changeCCButton.setOnClickListener { changeCreditCard() }
    }

    fun changeCreditCard() {
        creditCardNo = changeCCCreditCardNoText.text.toString()
        cvvCode = changeCCCvvCodeText.text.toString()
        email = changeCCEmailText.text.toString()
        password = changeCCPasswordText.text.toString()

        if (!dataIsValid())
            return

        val queue = Volley.newRequestQueue(this)
        val url = getString(R.string.backend_url) + "/settings"
        jsonObj.put("Setting", "CreditCard")
        jsonObj.put("Value", "$creditCardNo|$cvvCode")
        jsonObj.put("Email", email)
        jsonObj.put("Password", password)
        val jsonRequest = JsonObjectRequest(Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    Toast.makeText(this,"Credit card details updated", Toast.LENGTH_LONG).show()
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

    fun dataIsValid(): Boolean {
        return true
    }
}