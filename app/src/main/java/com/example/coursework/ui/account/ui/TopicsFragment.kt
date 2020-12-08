package com.example.coursework.ui.account.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.coursework.R

class TopicsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topics2, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefButton: Button = view.findViewById(R.id.save_topics)
        val resetButton : Button = view.findViewById(R.id.reset_topics)
        val toggleButtonGeneral : ToggleButton = view.findViewById(R.id.toggle_general)
        val toggleButtonEntertainment: ToggleButton = view.findViewById(R.id.toggle_entertainment)
        val toggleButtonBusiness: ToggleButton = view.findViewById(R.id.toggle_business)
        val toggleButtonScience: ToggleButton = view.findViewById(R.id.toggle_science)
        val toggleButtonHealth: ToggleButton = view.findViewById(R.id.toggle_health)
        val toggleButtonSports: ToggleButton = view.findViewById(R.id.toggle_sports)
        val toggleButtonTechnology: ToggleButton = view.findViewById(R.id.toggle_technology)
        val spinnerLanguage : Spinner = view.findViewById(R.id.category_preferred_language)
        resetButton.setOnClickListener{
            val preferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.clear()
            editor?.commit()
            Toast.makeText(context, "Topics are reset! Be sure to choose new", Toast.LENGTH_LONG).show()
        }

        prefButton.setOnClickListener {
            val preferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.putBoolean("entertainment", toggleButtonEntertainment.isChecked)
            editor?.putBoolean("business", toggleButtonBusiness.isChecked)
            editor?.putBoolean("general", toggleButtonGeneral.isChecked)
            editor?.putBoolean("science", toggleButtonScience.isChecked)
            editor?.putBoolean("health", toggleButtonHealth.isChecked)
            editor?.putBoolean("sports", toggleButtonSports.isChecked)
            editor?.putBoolean("technology", toggleButtonTechnology.isChecked)
            var country = spinnerLanguage.selectedItem.toString().
            substring(spinnerLanguage.selectedItem.toString().length-2,
                spinnerLanguage.selectedItem.toString().length)
            println(country)
            if (country=="ne") {
                country=""
            }
            println("the country selected is this:" + country)
            editor?.putString("preferred_country",country)
            editor?.commit()
            Toast.makeText(context, "Your topics are saved!", Toast.LENGTH_SHORT).show()
        }




    }


}