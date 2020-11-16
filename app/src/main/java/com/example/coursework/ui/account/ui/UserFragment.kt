package com.example.coursework.ui.account.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coursework.FireBase.LoginActivity
import com.example.coursework.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class UserFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_user, container, false)
        val buttonChangePassword : Button = root.findViewById(R.id.button_change_password)

        auth = FirebaseAuth.getInstance()
        buttonChangePassword.setOnClickListener{
            changePassword(root.findViewById(R.id.current_password),
                root.findViewById(R.id.new_password),
                root.findViewById(R.id.confirm_password))
        }




        // Inflate the layout for this fragment
        return root

    }

    private fun changePassword(
        current_password: EditText,
        new_password: EditText,
        confirm_password: EditText
    ) {
        if (current_password.text.isNotEmpty() &&
                new_password.text.isNotEmpty() &&
                confirm_password.text.isEmpty() ) {
            if (new_password.text.toString().equals(confirm_password.text.toString())) {

                val user: FirebaseUser? = auth.currentUser

                if(user!=null && user.email!=null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, current_password.text.toString())

                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context,"Re-authentication sucess",Toast.LENGTH_SHORT).show()
                                user.updatePassword(new_password.text.toString())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(context,"Password has changed",Toast.LENGTH_SHORT).show()
                                            auth.signOut()
                                            val intent = Intent(activity, LoginActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }
                            } else {
                                Toast.makeText(context,"Re-authentication failed",Toast.LENGTH_SHORT).show()

                            }
                        }
                } else {
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(context, "Password mismatching", Toast.LENGTH_SHORT).show()
            }


        }

    }


}