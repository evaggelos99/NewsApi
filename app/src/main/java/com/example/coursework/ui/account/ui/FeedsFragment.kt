package com.example.coursework.ui.account.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.Feed
import com.example.coursework.FeedAdapter
import com.example.coursework.R
import com.example.coursework.SwipeToDelete
import kotlin.collections.ArrayList


class FeedsFragment : Fragment() {

    lateinit var feedAdapter : FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_feeds, container, false)
        /*val reseButtonFeeds : Button = root.findViewById(R.id.reset_feeds)
        reseButtonFeeds.setOnClickListener {
            val preferences = activity?.getSharedPreferences("sources", Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.clear()?.commit()
            Toast.makeText(context, "Feeds are reset! Be sure to choose new", Toast.LENGTH_LONG).show()
        }*/

        val preferences = activity?.getSharedPreferences("sources", Context.MODE_PRIVATE)
        val sourceSet = preferences?.getStringSet("sources", null)
        val feeds : ArrayList<Feed> = arrayListOf()
        if (sourceSet != null) {
            for (source in sourceSet) {
                feeds.add(Feed(source))
            }
        }

        val recViewFeeds = root.findViewById(R.id.feed_recView) as RecyclerView
        activity?.runOnUiThread {
            val llm = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.VERTICAL
            recViewFeeds.setLayoutManager(llm)
            recViewFeeds.setAdapter(FeedAdapter(feeds))
            feedAdapter= FeedAdapter(feeds)
        }

        val item = object : SwipeToDelete(context, 0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                context?.let { feedAdapter.delete(viewHolder.adapterPosition, it) }
            }
        }
        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(recViewFeeds)




        return root;
    }


}
