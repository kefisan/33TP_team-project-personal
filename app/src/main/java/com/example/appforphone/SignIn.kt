package com.example.appforphone

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignIn(var credentialsManager: CredentialsManager) : Fragment(R.layout.activity_sing_in) {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signInLabel: TextView
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditTextField: TextInputEditText
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var mainL: ConstraintLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("FRAGMENTS", "Initializing values")

        usernameEditText = view.findViewById<TextInputLayout>(R.id.username).editText!!
        passwordEditText = view.findViewById<TextInputLayout>(R.id.passwordSignIn).editText!!
        loginButton = view.findViewById(R.id.login_button)
        signInLabel = view.findViewById(R.id.register_now)
        emailEditText = view.findViewById(R.id.usernameText)
        passwordEditTextField = view.findViewById(R.id.passwordText)
        passwordLayout = view.findViewById(R.id.passwordSignIn)
        emailLayout = view.findViewById(R.id.username)
        Log.d("Fragments", "Initialization before frame layout")
        mainL = view.findViewById(R.id.main)

        ViewCompat.setOnApplyWindowInsetsListener(mainL) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("FRAGMENTS", "Finished initializing values")

        usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val email = emailEditText.text.toString()
                if (!credentialsManager.isEmailValid(email)) {
                    usernameEditText.error = getString(R.string.error_invalid_email)
                    Log.d("Validation", "Invalid email: $email")
                } else {
                    usernameEditText.error = null
                }
            }
        })

        passwordEditTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val password = passwordEditTextField.text.toString()
                if (!credentialsManager.isPasswordValid(password)) {
                    passwordEditTextField.error = getString(R.string.error_invalid_password)
                    Log.d("Validation", "Invalid password")
                } else {
                    passwordEditTextField.error = null
                }
            }
        })

        signInLabel.setOnClickListener {
            Log.d("Onboarding", "Register now pressed")
            val registerFragment = RegisterNow(credentialsManager)
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flMain, registerFragment)
                addToBackStack("Sign In")
                commit()
            }
        }

        val loginErrorPopup = Snackbar.make(loginButton, "Wrong email or password", 10000)

        loginButton.setOnClickListener {
            Log.d("Credentials", "Login button pressed")
            val inputPassword = passwordEditText.text.toString()
            val inputEmail = emailEditText.text.toString()

            var isValid = true

            if (!credentialsManager.isPasswordValid(inputPassword)) {
                passwordLayout.error = "Invalid Password"
                isValid = false
            } else {
                passwordLayout.error = null
            }

            if (!credentialsManager.isEmailValid(inputEmail)) {
                emailLayout.error = "Invalid Email"
                isValid = false
            } else {
                emailLayout.error = null
            }

            if (isValid && !credentialsManager.doesPasswordMatchEmail(inputEmail, inputPassword)) {
                loginErrorPopup.show()
            } else if (isValid) {
                var gotoWelcomeScreen = Intent(getActivity(), ListActivity::class.java)
                startActivity(gotoWelcomeScreen)
            }
        }
        super.onStart()
        Log.d("SignInFragment", "onStart called")
    }

    fun changeFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.flMain, fragment)
            addToBackStack("Sign In")
            commit()
        }
    }
}

