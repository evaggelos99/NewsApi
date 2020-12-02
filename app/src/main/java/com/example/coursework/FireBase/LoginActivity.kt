package com.example.coursework.FireBase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coursework.MainActivity
import com.example.coursework.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        val buttonToRegisterScreen : Button = findViewById(R.id.button_login_register)
        buttonToRegisterScreen.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val buttonLogIn : Button = findViewById(R.id.button_login)
        buttonLogIn.setOnClickListener{
            logIn()
        }
    }

    private fun logIn() {
        val emailText : EditText = findViewById(R.id.email_login)
        val passwordText : EditText = findViewById(R.id.password_login)

        if (emailText.text.toString().isEmpty()) {
            emailText.error = "Please Enter email"
            emailText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString()).matches()) {
            emailText.error = "Please enter valid Email"
            emailText.requestFocus()
            return
        }

        if (passwordText.text.toString().isEmpty()) {
            passwordText.error = "Please Enter password"
            passwordText.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        updateUI(null)
                        // ...
                    }

                    // ...
                }
    }


    public override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser!=null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(baseContext,"You are already logged in", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(baseContext, "Please verify your email address",
                        Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(baseContext, "Log in failed.",
                    Toast.LENGTH_SHORT).show()
        }
    }


}