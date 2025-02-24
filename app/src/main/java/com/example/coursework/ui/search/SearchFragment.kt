package com.example.coursework.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.coursework.*
import com.example.coursework.Adapters.onItemClickListener


class SearchFragment : Fragment(),
    onItemClickListener {



    val BASE_URL = "https://newsapi.org/v2"
    val TOP_HEADLINES="/top-headlines?"
    val API_KEY="apiKey=bb5340ea2839447eb75d2e5515ab6081"

    //params
    val COUNTRY = "country="
    val CATEGORY = "category="


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_search, container, false)
        val searchButton: Button = root.findViewById(R.id.search_button)


        searchButton.setOnClickListener {

            val spinnerCountry : Spinner = root.findViewById(R.id.country_list_spinner)
            var country = countryString(spinnerCountry)
            val spinnerCategory : Spinner = root.findViewById(R.id.category_list_spinner)
            var category = categoryString(spinnerCategory)
            val keywordInput : EditText = root.findViewById(R.id.Keyword_Input)

            var keyword = keywordInput.text.toString()

            if (keyword=="") {
                keyword = ""
            } else keyword= "q=$keyword&"




            val bundle = Bundle()
            bundle.putString("country", country)
            bundle.putString("category",category)
            bundle.putString("keyword", keyword)
            val newFrag = SearchRecView()
            newFrag.arguments = bundle

            childFragmentManager.beginTransaction().replace(
                R.id.search_layout, newFrag).commit()

            val llaout = root.findViewById(R.id.search_child_layout) as LinearLayout
            llaout.visibility=View.GONE
        }
        return root
    }

    private fun categoryString(spinnerCategory: Spinner): String {
        var category = spinnerCategory.selectedItem.toString()
        if (category == "None") {
            category = ""
        } else category = "$CATEGORY$category&"
        return category
    }

    private fun countryString(spinnerCountry: Spinner): String {
        var country = spinnerCountry.selectedItem.toString().substring(spinnerCountry.selectedItem.toString().length - 2,
                spinnerCountry.selectedItem.toString().length)
        if (country == "ne") {
            country = ""
        } else country = "$COUNTRY$country&"
        return country
    }


}
