package com.example.gamecompanion.fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamecompanion.Network.TwitchApiService
import com.example.gamecompanion.R
import com.example.gamecompanion.activity.LoginActivity
import com.example.gamecompanion.activity.RegisterActivity
import com.example.gamecompanion.model.StreamsResponse
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_USERS
import com.example.gamecompanion.util.SharePreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FriendsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    //Init /Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        TwitchApiService.endpoints.getStreams().enqueue(object : retrofit2.Callback<StreamsResponse>{

            override fun onFailure(call: retrofit2.Call<StreamsResponse>, t: Throwable) {
                //No internet
                Log.w("StreamsFragment",t)
            }

            override fun onResponse(call: retrofit2.Call<StreamsResponse>, response: retrofit2.Response<StreamsResponse>) {

                Log.i("StreamsFragment","++ on Response ++")
                if(response.isSuccessful){ //code ==200
                    //All good
                    Log.i("StreamsFragment",response.body()?.toString() ?: "Null body")
                } else{

                    //Not OK
                    Log.w("StreamsFragment",response.message())
                }

            }




        })


    }



}