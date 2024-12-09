package com.example.appforphone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class SignIn : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var credentialsManager: CredentialsManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_in)

        passwordEditText = findViewById<TextInputLayout>(R.id.passwordSignIn).editText!!
        usernameEditText = findViewById<TextInputLayout>(R.id.username).editText!!
        credentialsManager = CredentialsManager()

        usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            // some fix here

            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                val email = s.toString()
                val isValid = credentialsManager.isEmailValid(email) && credentialsManager.isPasswordValid(password)
                if (isValid) {
                    usernameEditText.error = null
                    passwordEditText.error = null
                } else {
                    usernameEditText.error = getString(R.string.error_invalid_email)
                    passwordEditText.error = getString(R.string.error_invalid_password)
                }
            }
        })

//        val registerScreenLabel = findViewById<TextView>(R.id.register_now)
//        registerScreenLabel.setOnClickListener {
//            Log.d("Onboarding", "Sign in pressed")
//
//            val email = mailEditText.text.toString().trim()
//            val isValidEmail = credentialsManager.isEmailValid(email)
//            if (isValidEmail) {
//                val goToRegisterIntent = Intent(this, SignIn::class.java)
//                startActivity(goToRegisterIntent)
//            } else {
//                Toast.makeText(this, getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show()
//            }

        val signInLabel = findViewById<TextView>(R.id.register_now)
        signInLabel.setOnClickListener {
            Log.d("Onboarding", "Register now pressed")

            val goToRegisterIntent = Intent(this, RegisterNow::class.java)
            startActivity(goToRegisterIntent)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val loginButton = findViewById<Button>(R.id.login_button)
        val passwordEditText = findViewById<TextInputEditText>(R.id.passwordText)
        val emailEditText = findViewById<TextInputEditText>(R.id.usernameText)
        val emailLayout = findViewById<TextInputLayout>(R.id.username)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordSignIn)
        val credentialsManager = CredentialsManager()
        val loginErrorPopup = Snackbar.make(loginButton,"Wrong email or password",10000)

        loginButton.setOnClickListener{
            Log.d("Credentials","Login button pressed")
            val inputPassword = passwordEditText.text.toString()
            val inputEmail = emailEditText.text.toString()

            if(!credentialsManager.isPasswordValid(inputPassword)){
                passwordLayout.error = "Invalid Password"
                return@setOnClickListener
            }
            else{
                passwordLayout.error = null
            }

            if(!credentialsManager.isEmailValid(inputEmail)){
                emailLayout.error = "Invalid Email"
                return@setOnClickListener
            }
            else{
                emailLayout.error = null
            }

            if(!credentialsManager.doesPasswordMatchEmail(inputEmail,inputPassword)) {
                loginErrorPopup.show()
            }
            else{
                startActivity(Intent(this@SignIn,MainActivity::class.java))
            }
        }
    }
}