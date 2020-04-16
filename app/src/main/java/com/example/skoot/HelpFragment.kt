package com.example.skoot

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.skoot.R
import kotlinx.android.synthetic.main.layout_help.*
import kotlinx.android.synthetic.main.layout_help.view.*
import kotlinx.android.synthetic.main.layout_help.view.helpRentingContent

class HelpFragment : Fragment() {

    companion object {
        fun newInstance(): HelpFragment = HelpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.layout_help, container, false)
    }

    private fun toggleText(tv: TextView) {
        if (tv.visibility == View.VISIBLE)
            tv.visibility = View.INVISIBLE
        else
            tv.visibility = View.VISIBLE
    }
}
