import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skoot.R
import kotlinx.android.synthetic.main.layout_wallet.view.*

class WalletFragment : Fragment() {

    companion object {
        fun newInstance(): WalletFragment = WalletFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.layout_wallet, container, false)

        view.walletFullName.text = arguments!!["Fname"].toString() + " " + arguments!!["Lname"]
        view.walletEmail.text = arguments!!["Email"].toString()
        view.walletCash.text = "20.00€"

        return view
    }
}