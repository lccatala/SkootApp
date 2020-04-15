package com.example.skoot

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_become_collector.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.layout_settings.*
import org.json.JSONObject

class MenuActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var fname: String
    private lateinit var lname: String

    private var jsonObj = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        Toast.makeText(this,"Henlo", Toast.LENGTH_LONG).show()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        fname = intent.getStringExtra("Fname")
        lname = intent.getStringExtra("Lname")
        email = intent.getStringExtra("Email")
        password = intent.getStringExtra("Password")

        var dataBundle = Bundle()
        dataBundle.putString("Email", email)
        dataBundle.putString("Password", password)
        dataBundle.putString("Fname", fname)
        dataBundle.putString("Lname", lname)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_wallet -> {
                    val fragment = WalletFragment.newInstance()
                    fragment.arguments = dataBundle
                    openFragment(fragment)
                    true
                }
                R.id.navigation_rides -> {
                    val fragment = RidesFragment.newInstance()
                    fragment.arguments = dataBundle
                    openFragment(fragment)
                    true
                }
                R.id.navigation_scanner -> {
                    val fragment = ScannerFragment.newInstance()
                    fragment.arguments = dataBundle
                    openFragment(fragment)
                    true
                }
                R.id.navigation_help -> {
                    val fragment = HelpFragment.newInstance()
                    fragment.arguments = dataBundle
                    openFragment(fragment)
                    true
                }
                R.id.navigation_settings -> {
                    val fragment = SettingsFragment.newInstance()
                    fragment.arguments = dataBundle
                    openFragment(fragment)
                    true
                }

                else ->true
            }
        }

        // Select ScannerFragment by default
        bottom_navigation.selectedItemId = R.id.navigation_scanner
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
