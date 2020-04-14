import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.skoot.LoginActivity
import com.example.skoot.R
import kotlinx.android.synthetic.main.layout_scanner.*
import kotlinx.android.synthetic.main.layout_scanner.view.*
import kotlinx.android.synthetic.main.layout_scanner.view.rentButton
import org.json.JSONObject

class ScannerFragment : Fragment() {

    private lateinit var scooterCode: String
    private var jsonObj = JSONObject()

    companion object {
        fun newInstance(): ScannerFragment = ScannerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.layout_scanner, container, false)
        view.rentButton.isEnabled = false
        view.stopRentButton.isEnabled = false
        getCurrentBooking(view)
        view.stopRentButton.setOnClickListener {
            stopRent()
        }
        view.rentButton?.setOnClickListener {
            scooterCode = view.scooterCodeText.text.toString()
            // TODO: validate user input
            rent()
        }

        return view
    }

    private fun rent() {
        val queue = Volley.newRequestQueue(activity)
        val url = getString(R.string.backend_url) + "/rent"
        jsonObj.put("Data", scooterCode)
        jsonObj.put("Email", arguments!!["Email"])
        jsonObj.put("Password", arguments!!["Password"])
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    if (response["CVV"].toString() == "available") {
                        Toast.makeText(activity,"You rented a scooter!", Toast.LENGTH_LONG).show()
                        rentButton.isEnabled = false
                        stopRentButton.isEnabled = true
                    } else {
                        Toast.makeText(activity,"That scooter is unavailable!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Something went wrong: " + error.toString(), Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }

    private fun stopRent() {
        rentButton.isEnabled = true
        stopRentButton.isEnabled = false
        val queue = Volley.newRequestQueue(activity)
        val url = getString(R.string.backend_url) + "/stopRent"
        jsonObj.put("Email", arguments!!["Email"])
        jsonObj.put("Password", arguments!!["Password"])
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    Toast.makeText(activity,"Your rental is over!", Toast.LENGTH_LONG).show()
                } else {
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Something went wrong: " + error.toString(), Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }

    private fun getCurrentBooking(view: View) {
        val queue = Volley.newRequestQueue(activity)
        val url = getString(R.string.backend_url) + "/getBooking"
        jsonObj.put("Email", arguments!!["Email"])
        jsonObj.put("Password", arguments!!["Password"])
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    if (response["CVV"].toString() != "") {
                        view.stopRentButton.isEnabled = true
                        view.rentButton.isEnabled = false
                    } else {
                        view.stopRentButton.isEnabled = false
                        view.rentButton.isEnabled = true
                    }
                } else {
                    Toast.makeText(activity,"Unauthorized user", Toast.LENGTH_LONG).show()
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Something went wrong: " + error.toString(), Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }
}