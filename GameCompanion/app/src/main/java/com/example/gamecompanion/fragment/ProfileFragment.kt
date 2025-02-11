package com.example.gamecompanion.fragment


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.gamecompanion.activity.LoginActivity
import com.example.gamecompanion.activity.RegisterActivity
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_USERS
import com.example.gamecompanion.util.SharePreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toFile
import com.squareup.picasso.Picasso
import com.example.gamecompanion.R
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamecompanion.activity.NewsAdapter

import com.example.gamecompanion.model.NewsModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.usernameTextView
import java.io.ByteArrayInputStream
import java.net.URI
import java.net.URL


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
                indeterminateBar.visibility=View.GONE
                loginButton.visibility = View.VISIBLE
                logoutButton.visibility= View.GONE
                registerButton.visibility = View.VISIBLE
                usernameTextView.visibility=View.GONE
                avatar.visibility = View.GONE
                takePicture.visibility = View.GONE
                registerButton.setOnClickListener {
                    //TODO: Go to Register
                    startActivity(Intent(requireContext(), RegisterActivity::class.java))
                }
                loginButton.setOnClickListener {
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                }
            } else {
                //2-User Available
                //hide register button
                indeterminateBar.visibility=View.GONE
                registerButton.visibility = View.GONE
                //else: Show Profile
                loginButton.visibility = View.GONE
                logoutButton.visibility = View.VISIBLE
                usernameTextView.visibility=View.VISIBLE
                avatar.visibility = View.VISIBLE
                takePicture.visibility = View.VISIBLE
                logoutButton.setOnClickListener {
                    //logout user
                    FirebaseAuth.getInstance().signOut()
                    //Clear SharedPreferences
                    SharePreferencesManager().clear(requireContext())
                    initUI()
                }
                showUser()
                takePicture.setOnClickListener(){
                    //Take photo
                    takePicture()

                }

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
                    usernameTextView.text = "${userProfile?.username?.capitalize()}"


                    //save locally
                    SharePreferencesManager().setUsername(requireContext(), userProfile?.username)


                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }

        }


        SharePreferencesManager().getProfilePictureUrl(requireContext())
            ?.let {profilePicture->
                //Si existe username fem Get

                Picasso.get().load(profilePicture).placeholder(R.drawable.ic_profile).into(avatar);

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


                    Picasso.get().load(userProfile?.profilePicture).placeholder(R.drawable.ic_profile).into(avatar);



                    //save locally
                    SharePreferencesManager().setProfilePictureUrl(requireContext(), userProfile?.profilePicture)

                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()


                }

        }


    }


    // region Picture

    private fun takePicture(){

        val imageIntent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageIntent.resolveActivity(requireActivity().packageManager)
        startActivityForResult(imageIntent,1)

    }


    //private fun createImageFile(){
      //  val file= File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.toString()+"/"+"avatarImage.jpeg") //ruta del arxiu

    //}

    private fun saveImageFileToCloud(bitmap: Bitmap){
        //convert image to bytes
        val baos =ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val imageBytes=baos.toByteArray()
        //get folder reference
        val storageReference = FirebaseStorage.getInstance().reference
        val avatarsFolderReference = storageReference.child("public/avatars/") //url d'una carpeta en concret
        val userID = FirebaseAuth.getInstance().currentUser?.uid?:""
        val timestamp=System.currentTimeMillis()
         val avatarImageReference = avatarsFolderReference.child("avatar_${userID}_${timestamp}.jpeg")

        //upload image
        avatarImageReference.putBytes(imageBytes)
            .addOnSuccessListener {
                //image upload succes
                //get download URL

                avatarImageReference.downloadUrl
                    .addOnSuccessListener { url->
                        //Got Image URL
                        //Save to user profile

                        FirebaseFirestore.getInstance()
                            .collection(COLECTION_USERS)
                            .document(userID)
                            .update("profilePicture",url.toString())
                            .addOnSuccessListener {
                                //success
                                indeterminateBar.visibility=View.GONE
                                Picasso.get().load(url).placeholder(R.drawable.ic_profile).into(avatar);
                                SharePreferencesManager().setProfilePictureUrl(requireContext(), url.toString())
                            }
                            .addOnFailureListener {
                                //error
                            }

                    }

            }
            .addOnFailureListener(){

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1){
            val imageBitmap = data?.extras?.get("data") as Bitmap  //bitmap=imatge a android
            imageBitmap?.let{
                //show in ImageView
                FirebaseAnalytics.getInstance(requireContext()).logEvent("Profile_Pic", null)
                indeterminateBar.visibility=View.VISIBLE
                avatar.setImageBitmap(it)
                //upload
                saveImageFileToCloud(it)
            }
        }
        else{
            Toast.makeText(requireContext(), getString(R.string.camera_error), Toast.LENGTH_LONG).show()
        }
    }

    //endregion
}

