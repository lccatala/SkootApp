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
import org.json.JSONObject


class ChangeCreditCardActivity : AppCompatActivity() {
    private lateinit var oldPassword: String
    private lateinit var newPassword: String
    private lateinit var email: String

    private var jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_credit_card)

    }


}