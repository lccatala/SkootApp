package com.example.skoot

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_settings.view.*
import org.json.JSONObject

class SettingsFragment : Fragment() {

    var jsonObj = JSONObject()

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

        view.settingsChangeNameButton.setOnClickListener {
            var intent = Intent(activity, ChangeNameActivity::class.java)
            startActivity(intent)
        }

        view.settingsChangeCreditCardButton.setOnClickListener {
            var intent = Intent(activity, ChangeCreditCardActivity::class.java)
            startActivity(intent)
        }

        view.settingsBecomeCollectorButton.setOnClickListener {
            var intent = Intent(activity, BecomeCollectorActivity::class.java)
            startActivity(intent)
        }

        view.logOutButton.setOnClickListener {
            var intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
