package com.example.coursework.ui.topics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coursework.R

class TopicsFragment : Fragment() {

    private lateinit var topicsViewModel: TopicsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        topicsViewModel =
                ViewModelProvider(this).get(TopicsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_topics, container, false)
        val textView: TextView = root.findViewById(R.id.text_topics)
        topicsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}