package com.example.gamecompanion.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gamecompanion.R
import com.example.gamecompanion.activity.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

        //Init /Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        }

    override fun onResume() {
        super.onResume()
        initUI()
    }

        private fun initUI() {
            //TODO: if user == null
            if (FirebaseAuth.getInstance().currentUser == null) {
                //show register buttom
                logoutButton.visibility= View.GONE
                registerButton.visibility = View.VISIBLE
                registerButton.setOnClickListener {
                    //TODO: Go to Register
                    startActivity(Intent(requireContext(), RegisterActivity::class.java))
                }
            } else {
                //hide register button
                registerButton.visibility = View.GONE
                //else: Show Profile
                logoutButton.visibility = View.VISIBLE
                logoutButton.setOnClickListener {
                    //logout user
                    FirebaseAuth.getInstance().signOut()
                    initUI()
                }
            }
        }
    }

