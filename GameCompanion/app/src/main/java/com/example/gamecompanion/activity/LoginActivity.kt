package com.example.gamecompanion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gamecompanion.R
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_USERS
import com.example.gamecompanion.util.SharePreferencesManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_profile.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        lateinit var auth: FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        //1 Listener
        loginButton.setOnClickListener {
            //2 Read TextFields
            val email = emailEditText.text?.toString().orEmpty()
            val password = passwordEditText.text?.toString().orEmpty()

            //3 Validacio
            //3.1 Username validation
            if(email.trim().isEmpty()){
                //Error
                Toast.makeText(this, "Email error", Toast.LENGTH_LONG).show()
                //or
                usernameEditText.error = "Email required"
                return@setOnClickListener

            }

            //3.3 Password Validation

            if(password.isBlank() || !isPasswordValid(password)){
                //Error
                Toast.makeText(this, "password error", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val user = auth.currentUser

                       //FirebaseAuth.getInstance().updateCurrentUser(user)
                        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                        FirebaseFirestore.getInstance()
                            .collection(COLECTION_USERS)
                            .document(userId)
                            .get()
                            .addOnSuccessListener { documentSnapshot ->
                                //Got User Profile
                                val userProfile = documentSnapshot.toObject(UserModel::class.java)


                                Toast.makeText(baseContext,  "Hello there, ${userProfile?.username?.capitalize()}",
                                    Toast.LENGTH_SHORT).show()



                            }
                        
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }

                    // ...
                }

            //4 Create User
            /*FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnSuccessListener {authResult ->
                    Toast.makeText(this, "Logged In", Toast.LENGTH_LONG).show()
                    //FirebaseAuth.getInstance().updateCurrentUser()
                    finish()
                }
                .addOnFailureListener {
                    //5 Handle Errors
                    Toast.makeText(this,it.localizedMessage, Toast.LENGTH_LONG).show()
                }*/
        }
    }


    private fun isPasswordValid(password: String): Boolean{

        //password >= 4 characters

        if(password.length < 4 )
            return false

        var digit =0
        var let =0

        //Contain letter and number

        for(letter in password){
            if(letter.isDigit())
                digit++
            else if(letter.isLetter())
                let++
        }

        if(digit== password.length)
            return false

        if(let==password.length)
            return false


        return true
    }
}