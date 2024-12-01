package com.example.appforphone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterNow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_now)
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