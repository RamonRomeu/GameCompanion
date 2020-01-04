package com.example.gamecompanion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.gamecompanion.R
import com.example.gamecompanion.model.UserModel
import com.example.gamecompanion.util.COLECTION_USERS
import com.example.gamecompanion.viewmodels.RegisterViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.emailEditText
import kotlinx.android.synthetic.main.activity_register.indeterminateBar
import kotlinx.android.synthetic.main.activity_register.passwordEditText


class RegisterActivity : AppCompatActivity() {

    var registerViewModel: RegisterViewModel = RegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerViewModel.isRegisterSuccess.observe(this, Observer { isUserCreated ->
            if(isUserCreated) {
                //Success Creating User Profile!
                Toast.makeText(this, "Success creating new user", Toast.LENGTH_LONG)
                    .show()
                //Close Activity
                finish()
            }
            /*else{
                Toast.makeText(this, "Error creating new user", Toast.LENGTH_LONG)
                    .show()
            }*/
        })

        registerViewModel.isLoading.observe(this, Observer{ isLoading ->
            indeterminateBar.visibility = if(isLoading) View.VISIBLE else View.GONE
        })


        indeterminateBar.visibility= View.GONE

        //1 Listener
        RegisterButton.setOnClickListener {
            //2 Read TextFields
            val username = usernameEditText.text?.toString().orEmpty()
            val email = emailEditText.text?.toString().orEmpty()
            val password = passwordEditText.text?.toString().orEmpty()

            //3 Validacio
            //3.1 Username validation
            if(!registerViewModel.isUsernameValid(username)){
                //Error
                Toast.makeText(this, getString(R.string.username_error),Toast.LENGTH_LONG).show()
                //or
                usernameEditText.error = "Username Required"
                return@setOnClickListener

            }
            //3.2 Email Validation
            if(!registerViewModel.isEmailValid(email)){
                //Error
                Toast.makeText(this, getString(R.string.email_error),Toast.LENGTH_LONG).show()
                emailEditText.error = "Email Required"
                return@setOnClickListener
            }
            //3.3 Password Validation

            if(!registerViewModel.isPasswordValid(password)){
                //Error
                Toast.makeText(this, getString(R.string.password_error),Toast.LENGTH_LONG).show()
                passwordEditText.error = "Password Required"
                return@setOnClickListener
            }

            //4 Create User
            registerViewModel.createUser(username, email, password)
        }
    }



}
