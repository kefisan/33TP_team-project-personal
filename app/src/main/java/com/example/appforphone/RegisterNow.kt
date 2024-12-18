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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class RegisterNow : AppCompatActivity() {

    private lateinit var mailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var credentialsManager: CredentialsManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_now)

        passwordEditText = findViewById<TextInputLayout>(R.id.password).editText!!
        mailEditText = findViewById<TextInputLayout>(R.id.mail).editText!!
        credentialsManager = CredentialsManager()

        mailEditText.addTextChangedListener(object : TextWatcher {
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
                    mailEditText.error = null
                    passwordEditText.error = null
                } else {
                    mailEditText.error = getString(R.string.error_invalid_email)
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
            Log.d("Onboarding", "Sign In pressed")

            val goToRegisterIntent = Intent(this, RegisterNow::class.java)
            startActivity(goToRegisterIntent)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val registerScreenLabel = findViewById<TextView>(R.id.register_now)
        registerScreenLabel.setOnClickListener {
            Log.d("Onboarding", "Sign in pressed")

            val goToRegisterIntent = Intent(this, SignIn::class.java)
            startActivity(goToRegisterIntent)
        }

        val emailText = findViewById<TextInputEditText>(R.id.mailRegisterText)
        val emailLayout = findViewById<TextInputLayout>(R.id.mail)
        val passwordText = findViewById<TextInputEditText>(R.id.passwordRegisterText)
        val passwordLayout = findViewById<TextInputLayout>(R.id.password)
        val buttonRegister = findViewById<Button>(R.id.register_button)

        buttonRegister.setOnClickListener{
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
            val credentialsManager = CredentialsManager()

            if(!credentialsManager.isEmailValid(email)){
                emailLayout.error = "Email is wrong"
                return@setOnClickListener
            }
            else{
                emailLayout.error = null
            }

            if(credentialsManager.isEmailTaken(email)){
                emailLayout.error = "Email is already taken"
                return@setOnClickListener
            }
            else{
                emailLayout.error = null
            }

            if(!credentialsManager.isPasswordValid(password)){
                passwordLayout.error = "Password should contain special symbols and a digit"
                return@setOnClickListener
            }
            else{
                passwordLayout.error = null
            }

            credentialsManager.addEmailToMap(email,password)
            val goToRegisterIntent = Intent(this, SignIn::class.java)
            startActivity(goToRegisterIntent)
        }
    }
}