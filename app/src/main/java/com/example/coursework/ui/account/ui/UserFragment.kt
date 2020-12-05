package com.example.coursework.ui.account.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coursework.FireBase.RegisterActivity
import com.example.coursework.R
import com.google.firebase.auth.FirebaseAuth


class UserFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonLogOut :Button = view.findViewById(R.id.button_log_out)
        auth = FirebaseAuth.getInstance()
        buttonLogOut.setOnClickListener {
            if (auth.currentUser!=null) {
                auth.signOut()
                val intent = Intent(activity, RegisterActivity::class.java)
                startActivity(intent)
                Toast.makeText(context,"The user is logged out", Toast.LENGTH_SHORT).show()
            }
        }


    }



}