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
        view.returnCollectedButon.isEnabled = false

        getCurrentBooking(view)
        checkCollector(view)
        view.stopRentButton.setOnClickListener { stopRent() }
        view.rentButton?.setOnClickListener { rent(view) }
        view.collectButton.setOnClickListener { collectScooter(view) }
        view.returnCollectedButon.setOnClickListener { returnCollectedScooter(view) }

        return view
    }

    private fun rent(view: View) {
        scooterCode = view.scooterCodeText.text.toString()
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
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Something went wrong: $error", Toast.LENGTH_LONG).show()
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
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Something went wrong: $error", Toast.LENGTH_LONG).show()
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
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Something went wrong: $error", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }

    private fun checkCollector(view: View) {
        val queue = Volley.newRequestQueue(activity)
        val url = getString(R.string.backend_url) + "/getCollector"
        jsonObj.put("Email", arguments!!["Email"])
        jsonObj.put("Password", arguments!!["Password"])
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    Log.d("Collecting", response["CreditCardNo"].toString())
                    view.collectButton.isEnabled = response["CVV"].toString() == "true"
                    view.returnCollectedButon.isEnabled = response["CreditCardNo"].toString() == "true"
                } else {
                    Toast.makeText(activity,"Unauthorized user", Toast.LENGTH_LONG).show()
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Something went wrong: $error", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }

    private fun collectScooter(view: View) {
        scooterCode = view.scooterCodeText.text.toString()
        val queue = Volley.newRequestQueue(activity)
        val url = getString(R.string.backend_url) + "/collect"
        jsonObj.put("Data", scooterCode)
        jsonObj.put("Email", arguments!!["Email"])
        jsonObj.put("Password", arguments!!["Password"])
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    if (response["CVV"].toString() == "available") {
                        Toast.makeText(activity,"You collected a scooter!", Toast.LENGTH_LONG).show()
                        collectButton.isEnabled = false
                        returnCollectedButon.isEnabled = true
                    } else {
                        Toast.makeText(activity,"That scooter is unavailable!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(activity,"Unauthorized user", Toast.LENGTH_LONG).show()
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Something went wrong: $error", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }

    private fun returnCollectedScooter(view: View) {
        scooterCode = view.scooterCodeText.text.toString()
        val queue = Volley.newRequestQueue(activity)
        val url = getString(R.string.backend_url) + "/returnCollected"
        jsonObj.put("Data", scooterCode)
        jsonObj.put("Email", arguments!!["Email"])
        jsonObj.put("Password", arguments!!["Password"])
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    if (response["CVV"].toString() == "unavailable") {
                        Toast.makeText(activity,"Thank you for charging a scooter!", Toast.LENGTH_LONG).show()
                        collectButton.isEnabled = true
                        returnCollectedButon.isEnabled = false
                    } else {
                        Toast.makeText(activity,"That scooter is not being charged!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity,"Something went wrong: $error", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonRequest)
    }
}