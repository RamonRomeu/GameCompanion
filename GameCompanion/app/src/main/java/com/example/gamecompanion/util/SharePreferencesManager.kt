package com.example.gamecompanion.util

import android.content.Context
import android.widget.Toast
import com.example.gamecompanion.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*

class SharePreferencesManager {

    val userPreferencesFileName = "userPreferences"
    val usernameKey="username"
    val profilePictureKey="picture"



    fun getUsername(context: Context): String?{

        val sharedPreferences= context.getSharedPreferences(userPreferencesFileName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(usernameKey,null)
    }

    fun setUsername(context: Context, username: String?){

        val sharedPreferences= context.getSharedPreferences(userPreferencesFileName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(usernameKey,username).apply()
    }

    fun getProfilePictureUrl(context: Context): String?{
        val sharedPreferences= context.getSharedPreferences(userPreferencesFileName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(profilePictureKey,null)
    }

    fun setProfilePictureUrl(context: Context, profilePicture: String?){

        val sharedPreferences= context.getSharedPreferences(userPreferencesFileName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(profilePictureKey,profilePicture).apply()
    }


    fun clear(context: Context){
        val sharedPreferences=context.getSharedPreferences(userPreferencesFileName,Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

    }
}