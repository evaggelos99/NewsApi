package com.example.coursework.ui.account.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.example.coursework.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [TopicsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopicsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_topics2, container, false)
        val prefButton: Button = root.findViewById(R.id.save_topics)
        val resetButton : Button = root.findViewById(R.id.reset_topics)
        val toggleButtonEntertainment: ToggleButton = root.findViewById(R.id.toggle_entertainment)
        val toggleButtonBusiness: ToggleButton = root.findViewById(R.id.toggle_business)
        val toggleButtonScience: ToggleButton = root.findViewById(R.id.toggle_science)
        val toggleButtonHealth: ToggleButton = root.findViewById(R.id.toggle_health)
        val toggleButtonSports: ToggleButton = root.findViewById(R.id.toggle_sports)
        val toggleButtonTechnology: ToggleButton = root.findViewById(R.id.toggle_technology)
        val spinnerLanguage : Spinner = root.findViewById(R.id.category_preferred_language)



        resetButton.setOnClickListener{
            val preferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.clear()?.commit()
            Toast.makeText(context, "Topics are reset! Be sure to choose new", Toast.LENGTH_LONG).show()

        }

        prefButton.setOnClickListener {
            val preferences = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.putBoolean("entertainment", toggleButtonEntertainment.isChecked)
            editor?.putBoolean("business", toggleButtonBusiness.isChecked)
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
            Toast.makeText(context, "Topics saved!", Toast.LENGTH_SHORT).show()
    }





        // Inflate the layout for this fragment
        return root
    }
}