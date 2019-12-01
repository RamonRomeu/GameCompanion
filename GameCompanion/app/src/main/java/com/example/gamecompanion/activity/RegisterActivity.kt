package com.example.gamecompanion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.gamecompanion.R
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_USERS
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.emailEditText
import kotlinx.android.synthetic.main.activity_register.indeterminateBar
import kotlinx.android.synthetic.main.activity_register.passwordEditText


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        indeterminateBar.visibility= View.GONE

        //1 Listener
        RegisterButton.setOnClickListener {
            //2 Read TextFields
            val username = usernameEditText.text?.toString().orEmpty()
            val email = emailEditText.text?.toString().orEmpty()
            val password = passwordEditText.text?.toString().orEmpty()

            //3 Validacio
            //3.1 Username validation
            if(username.trim().isEmpty()){
                //Error
                Toast.makeText(this, getString(R.string.username_error),Toast.LENGTH_LONG).show()
                //or
                usernameEditText.error = "Username Required"
                return@setOnClickListener

            }
            //3.2 Email Validation
            if(email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                //Error
                Toast.makeText(this, getString(R.string.email_error),Toast.LENGTH_LONG).show()
                emailEditText.error = "Email Required"
                return@setOnClickListener
            }
            //3.3 Password Validation

            if(password.isBlank() || !isPasswordValid(password)){
                //Error
                Toast.makeText(this, getString(R.string.password_error),Toast.LENGTH_LONG).show()
                passwordEditText.error = "Password Required"
                return@setOnClickListener
            }

            //4 Create User
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener { authResult ->
                    indeterminateBar.visibility= View.VISIBLE
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
                            FirebaseAnalytics.getInstance(this).logEvent("Register_Success", null)
                            Toast.makeText(this, "Success creating new user",Toast.LENGTH_LONG).show()
                            finish()
                        }
                        .addOnFailureListener {
                            //handle errors
                            Toast.makeText(this, it.localizedMessage,Toast.LENGTH_LONG).show()
                        }


                }
                .addOnFailureListener {
                    //5 Handle Errors
                    indeterminateBar.visibility= View.GONE
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
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
