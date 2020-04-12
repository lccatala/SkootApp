package com.example.skoot

import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var fname: String
    private lateinit var lname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        email = intent.getStringExtra("Email")
        fname = intent.getStringExtra("Fname")
        lname = intent.getStringExtra("Lname")

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_wallet -> {
                    val fragment = WalletFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.navigation_rides -> {
                    val fragment = RidesFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.navigation_help -> {
                    val fragment = HelpFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.navigation_settings -> {
                    val fragment = SettingsFragment.newInstance()
                    openFragment(fragment)
                    true
                }

                else ->true
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
