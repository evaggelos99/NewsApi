package com.example.coursework.ui.account.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.coursework.R

class FeedsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_feeds, container, false)
        val reseButtonFeeds : Button = root.findViewById(R.id.reset_feeds)
        reseButtonFeeds.setOnClickListener {
            val preferences = activity?.getSharedPreferences("sources", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.clear()?.commit()
            Toast.makeText(context, "Feeds are reset! Be sure to choose new", Toast.LENGTH_LONG).show()
        }

        return root;
    }

}