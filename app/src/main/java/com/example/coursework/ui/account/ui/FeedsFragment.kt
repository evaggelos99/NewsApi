package com.example.coursework.ui.account.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coursework.*
import kotlin.collections.ArrayList


class FeedsFragment : Fragment() {

    lateinit var feedAdapter : SimpleFeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feeds, container, false);
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences = activity?.getSharedPreferences("sources", Context.MODE_PRIVATE)
        val sourceSet = preferences?.getStringSet("sources", null)
        val feeds : ArrayList<FeedSource> = arrayListOf()
        if (sourceSet != null) {
            for (source in sourceSet) {
                feeds.add(FeedSource(source))
            }
        }

        val recViewFeeds = view.findViewById(R.id.feed_recView) as RecyclerView

        activity?.runOnUiThread {
            val llm = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.VERTICAL
            recViewFeeds.setLayoutManager(llm)
            recViewFeeds.setAdapter(SimpleFeedAdapter(feeds))
            feedAdapter= SimpleFeedAdapter(feeds)
        }


        val item = object : SwipeToDelete(context, 0, ItemTouchHelper.LEFT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                context?.let { feedAdapter.delete(viewHolder.adapterPosition, it) }
            }
        }

        val itemTouchHelper = ItemTouchHelper(item)
        itemTouchHelper.attachToRecyclerView(recViewFeeds)




    }



}
