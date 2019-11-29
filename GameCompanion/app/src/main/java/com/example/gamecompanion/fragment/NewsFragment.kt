package com.example.gamecompanion.fragment


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamecompanion.R
import com.example.gamecompanion.activity.LoginActivity
import com.example.gamecompanion.activity.NewsAdapter
import com.example.gamecompanion.activity.RegisterActivity
import com.example.gamecompanion.model.NewsModel
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_NEWS
import com.example.gamecompanion.util.COLECTION_USERS
import com.example.gamecompanion.util.SharePreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    //Init /Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Layout Type
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Create Adapter
        val newsAdapter = NewsAdapter()

        FirebaseFirestore.getInstance()
            .collection(COLECTION_NEWS)
            .get()
            .addOnSuccessListener { document ->

                val newsList = document.toObjects(NewsModel::class.java)
                newsAdapter.list = ArrayList(newsList.toList())
                // RecyclerView <> Adapter
                recyclerView.adapter = newsAdapter
            }


    }

    override fun onResume() {
        super.onResume()
        //initUI()
    }


}