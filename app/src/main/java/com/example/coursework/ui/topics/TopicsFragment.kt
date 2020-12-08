package com.example.coursework.ui.topics


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.coursework.*
import com.example.coursework.ui.topics.allTopics.*


class TopicsFragment : Fragment(), onItemClickListener {


    val BASE_URL = "https://newsapi.org/v2"
    val TOP_HEADLINES="/top-headlines?"
    val API_KEY="apiKey=bb5340ea2839447eb75d2e5515ab6081"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_topics, container, false)

        return root
        }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val businessBtn : Button = view.findViewById(R.id.business)
        val entertainmentBtn : Button = view.findViewById(R.id.entertainment)
        val generalBtn : Button = view.findViewById(R.id.general)
        val healthBtn : Button = view.findViewById(R.id.health)
        val scienceBtn : Button = view.findViewById(R.id.science)
        val sportsBtn : Button = view.findViewById(R.id.sports)
        val technologyBtn : Button = view.findViewById(R.id.technology)

        businessBtn.setOnClickListener {
            val newFrag = BusinessFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        entertainmentBtn.setOnClickListener {
            val newFrag = EntertainmentFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        generalBtn.setOnClickListener {
            val newFrag = GeneralFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        healthBtn.setOnClickListener {
            val newFrag = HealthFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        scienceBtn.setOnClickListener {
            val newFrag = ScienceFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        sportsBtn.setOnClickListener {
            val newFrag = SportsFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }

        technologyBtn.setOnClickListener {
            val newFrag = TechnologyFragment()
            childFragmentManager.beginTransaction().replace(
                R.id.topic_layout, newFrag).commit()

            val llaout = view.findViewById(R.id.child_topic_layout) as LinearLayout
            llaout.visibility=View.GONE
        }


    }


}