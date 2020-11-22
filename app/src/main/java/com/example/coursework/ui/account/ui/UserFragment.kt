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
import com.example.coursework.FireBase.RegisterActivity
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
        val buttonLogOut :Button = root.findViewById(R.id.button_log_out)
        auth = FirebaseAuth.getInstance()
        buttonLogOut.setOnClickListener {
            if (auth.currentUser!=null) {
                auth.signOut()
                val intent = Intent(activity, RegisterActivity::class.java)
                startActivity(intent)
                Toast.makeText(context,"The user is logged out", Toast.LENGTH_SHORT).show()
            }
        }


        // Inflate the layout for this fragment
        return root

    }




}