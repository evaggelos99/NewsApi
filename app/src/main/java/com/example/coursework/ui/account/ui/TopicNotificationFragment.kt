package com.example.coursework.ui.account.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.ToggleButton
import com.example.coursework.MyService
import com.example.coursework.R

class TopicNotificationFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topic_notification, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toggleButtonGeneral : ToggleButton = view.findViewById(R.id.topic_toggle_general)
        val toggleButtonEntertainment: ToggleButton = view.findViewById(R.id.topic_toggle_entertainment)
        val toggleButtonBusiness: ToggleButton = view.findViewById(R.id.topic_toggle_business)
        val toggleButtonScience: ToggleButton = view.findViewById(R.id.topic_toggle_science)
        val toggleButtonHealth: ToggleButton = view.findViewById(R.id.topic_toggle_health)
        val toggleButtonSports: ToggleButton = view.findViewById(R.id.topic_toggle_sports)
        val toggleButtonTechnology: ToggleButton = view.findViewById(R.id.topic_toggle_technology)
        val buttonNotifReset : Button = view.findViewById(R.id.reset_notification)
        val buttonNotiApply : Button = view.findViewById(R.id.apply_notifications)

        buttonNotifReset.setOnClickListener{
            val preferences = activity?.getSharedPreferences("notifications", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.clear()
            editor?.commit()
            Toast.makeText(context, "Notifications are reset! Be sure to choose new", Toast.LENGTH_LONG).show()

        }

        buttonNotiApply.setOnClickListener {
            val preferences = activity?.getSharedPreferences("notifications", Context.MODE_PRIVATE)
            val editor = preferences?.edit()

            editor?.putBoolean("entertainment", toggleButtonEntertainment.isChecked)
            editor?.putBoolean("business", toggleButtonBusiness.isChecked)
            editor?.putBoolean("general", toggleButtonGeneral.isChecked)
            editor?.putBoolean("science", toggleButtonScience.isChecked)
            editor?.putBoolean("health", toggleButtonHealth.isChecked)
            editor?.putBoolean("sports", toggleButtonSports.isChecked)
            editor?.putBoolean("technology", toggleButtonTechnology.isChecked)

            editor?.commit()
            Toast.makeText(context, "Your notifications are set", Toast.LENGTH_SHORT).show()
            //val intent = Intent(context, MyService::class.java)
            //context?.startService(intent)

        }

    }
}