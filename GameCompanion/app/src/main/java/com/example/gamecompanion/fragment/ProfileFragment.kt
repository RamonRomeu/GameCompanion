package com.example.gamecompanion.fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gamecompanion.R
import com.example.gamecompanion.activity.RegisterActivity
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_USERS
import com.example.gamecompanion.util.SharePreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
                //1-No user
                //show register buttom
                logoutButton.visibility= View.GONE
                registerButton.visibility = View.VISIBLE
                usernameTextView.visibility=View.GONE
                registerButton.setOnClickListener {
                    //TODO: Go to Register
                    startActivity(Intent(requireContext(), RegisterActivity::class.java))
                }
            } else {
                //2-User Available
                //hide register button
                registerButton.visibility = View.GONE
                //else: Show Profile
                logoutButton.visibility = View.VISIBLE
                usernameTextView.visibility=View.VISIBLE
                logoutButton.setOnClickListener {
                    //logout user
                    FirebaseAuth.getInstance().signOut()
                    //Clear SharedPreferences
                    SharePreferencesManager().clear(requireContext())
                    initUI()
                }
                showUser();

            }
        }


    private fun showUser(){

        SharePreferencesManager().getUsername(requireContext())
            ?.let {username->
                //Si existe username fem Get
                usernameTextView.text= "Hello ${username.capitalize()}"
            }?: run {
            //No username

            //Get User Profile (from FireStore)
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            FirebaseFirestore.getInstance()
                .collection(COLECTION_USERS)
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    //Got User Profile
                    val userProfile = documentSnapshot.toObject(UserModel::class.java)
                    usernameTextView.text = "Hello ${userProfile?.username?.capitalize()}"
                    //save locally
                    SharePreferencesManager().setUsername(requireContext(), userProfile?.username)

                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }

        }
    }
}

