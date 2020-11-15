package com.example.coursework.FireBase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.coursework.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()



        val buttonLoginSignUp : Button = findViewById(R.id.button_register_login)
        buttonLoginSignUp.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val buttonSignUp : Button = findViewById(R.id.button_signUp)
        val emailText : EditText = findViewById(R.id.email_signUp)
        val passwordText : EditText = findViewById(R.id.password_signUp)
        buttonSignUp.setOnClickListener{

            if (emailText.text.toString().isEmpty()) {
                emailText.error = "Please Enter email"
                emailText.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString()).matches()) {
                emailText.error = "Please enter valid Email"
                emailText.requestFocus()
                return@setOnClickListener
            }

            if (passwordText.text.toString().isEmpty()) {
                passwordText.error = "Please Enter password"
                passwordText.requestFocus()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        user?.sendEmailVerification()?.addOnCompleteListener{ task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }
                        }
                        // Sign in success, update UI with the signed-in user's information

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Sign up failed",
                            Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
        }


    }
}