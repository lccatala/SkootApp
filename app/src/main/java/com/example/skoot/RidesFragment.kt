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
import kotlinx.android.synthetic.main.layout_rides.view.*
import org.json.JSONObject

class RidesFragment : Fragment() {

    private var jsonObj = JSONObject()
    private lateinit var rides: Array<String>

    companion object {
        fun newInstance(): RidesFragment = RidesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.layout_rides, container, false)
        loadRides(view)
        return view
    }

    fun loadRides(view: View) {
        val queue = Volley.newRequestQueue(activity)
        val url = getString(R.string.backend_url) + "/rides"
        jsonObj.put("Email", arguments!!["Email"])
        jsonObj.put("Password", arguments!!["Password"])
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObj,
            Response.Listener { response ->
                if (response["Authorized"] as Boolean) {
                    rides = Array(5) {response.optJSONArray("Rides")[it].toString()}
                    view.ride0.text = rides[0]
                    view.ride1.text = rides[1]
                    view.ride2.text = rides[2]
                    view.ride3.text = rides[3]
                    view.ride4.text = rides[4]
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