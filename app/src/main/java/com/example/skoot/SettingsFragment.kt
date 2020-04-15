package com.example.skoot

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skoot.R
import kotlinx.android.synthetic.main.layout_settings.view.*

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.layout_settings, container, false)

        view.settingsChangeEmailButton.setOnClickListener {
            var intent = Intent(activity, ChangeEmailActivity::class.java)
            startActivity(intent)
        }

        view.settingsChangePasswordButton.setOnClickListener {
            var intent = Intent(activity, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        view.logOutButton.setOnClickListener {
            var intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
