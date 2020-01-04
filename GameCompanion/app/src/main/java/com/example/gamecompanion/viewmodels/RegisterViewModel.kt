package com.example.gamecompanion.viewmodels

import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_USERS
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterViewModel: ViewModel() {

    val isRegisterSuccess = MutableLiveData<Boolean>().apply { value = false }
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val registerErrors = MutableLiveData<String?>().apply { value = null }

    fun isUsernameValid(username: String): Boolean {
        return !username.trim().isEmpty()
    }

    fun isEmailValid(email: String): Boolean {
        return !email.isBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
            //password >= 4 characters
            if(password.isBlank()) return false
            if(password.count() < 4 )
                return false
            var hasDigit = false
            var hasLetter = false

            //Contain letter and number
            for(character in password){
                if(character.isLetter()) hasLetter = true
                if(character.isDigit()) hasDigit = true
            }

            return hasLetter && hasDigit
    }

    fun createUser(username: String, email: String, password: String)
    {
        isLoading.postValue(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener { authResult ->
                //indeterminateBar.visibility= View.VISIBLE
                //create user profile
                val userModel = UserModel(
                    userId = authResult.user?.uid ?: "",
                    username = username,
                    email = email

                )
                //Add to Firebase
                FirebaseFirestore.getInstance()
                    .collection(COLECTION_USERS)
                    .document(authResult.user?.uid ?:"")
                    .set(userModel)
                    //succes creating user
                    .addOnSuccessListener {
                        isLoading.postValue(false)
                        isRegisterSuccess.postValue(true)
                    }
                    .addOnFailureListener {
                        //handle errors
                        isLoading.postValue(false)
                        //Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            }
            .addOnFailureListener {
                //5 Handle Errors
                isLoading.postValue(false)
                //Toast.makeText(this,it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
}