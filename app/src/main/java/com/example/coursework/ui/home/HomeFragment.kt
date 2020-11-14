package com.example.coursework.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.Article
import com.example.coursework.MyAdapter
import com.example.coursework.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        /*val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        val recyclerView = root.findViewById(R.id.recyclerView_home) as RecyclerView
        recyclerView.layoutManager =
                LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        val articles = ArrayList<Article> ()
        /*articles.add(Article("Article 1", "description for Article 1"))
        articles.add(Article("Article 2", "description for Article 2"))
        articles.add(Article("Article 3", "description for Article 3"))
        articles.add(Article("Article 4", "description for Article 4"))
        articles.add(Article("Article 5", "description for Article 5"))
        articles.add(Article("Article 6", "description for Article 6"))

        val adapter = MyAdapter(articles)*/

        //recyclerView.adapter = adapter
        return root
    }
}