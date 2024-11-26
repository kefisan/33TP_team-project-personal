package com.example.appforphone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//this code causes error, but the main idea is here, so I want to clarify it on the lesson

class RegisterNow : AppCompatActivity() {

    private lateinit var mailEditText: EditText
    private lateinit var credentialsManager: CredentialsManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_now)

        mailEditText = findViewById(R.id.mail)
        credentialsManager = CredentialsManager()

        mailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                val isValid = credentialsManager.isEmailValid(email)
                if (isValid) {
                    mailEditText.error = null
                } else {
                    mailEditText.error = getString(R.string.error_invalid_email)
                }
            }
        })

        //this can be changed and separated after fixing the main issue

        val registerScreenLabel = findViewById<TextView>(R.id.register_now)
        registerScreenLabel.setOnClickListener {
            Log.d("Onboarding", "Sign in pressed")

            val email = mailEditText.text.toString().trim()
            val isValidEmail = credentialsManager.isEmailValid(email)
            if (isValidEmail) {
                val goToRegisterIntent = Intent(this, SignIn::class.java)
                startActivity(goToRegisterIntent)
            } else {
                Toast.makeText(this, getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show()
            }


        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}