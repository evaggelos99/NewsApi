package com.example.coursework.ui.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.example.coursework.FireBase.RegisterActivity
import com.example.coursework.R
import com.example.coursework.ui.account.ui.AddFeedsFragment
import com.example.coursework.ui.account.ui.FeedsFragment
import com.example.coursework.ui.account.ui.TopicsFragment
import com.google.firebase.auth.FirebaseAuth


class AccountFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var llaout : LinearLayout
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        auth = FirebaseAuth.getInstance()
        llaout = root.findViewById(R.id.fragment_account_container)
        val chartView = root.findViewById(R.id.chart) as AnyChartView
        val totalClicksText = root.findViewById(R.id.total_clicks) as TextView
        val pie = AnyChart.pie()
        val buttonLogOut = root.findViewById(R.id.button_log_out) as Button

        val preferences = activity?.getSharedPreferences("clicks", Context.MODE_PRIVATE)
        val businessClicks = preferences?.getInt("businessClicks", 0)
        val generalClicks = preferences?.getInt("generalClicks", 0)
        val entertainmentClicks = preferences?.getInt("entertainmentClicks", 0)
        val healthClicks = preferences?.getInt("healthClicks", 0)
        val scienceClicks = preferences?.getInt("scienceClicks", 0)
        val sportsClicks = preferences?.getInt("sportsClicks", 0)
        val technologyClicks = preferences?.getInt("technologyClicks", 0)

        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry("Business", businessClicks))
        data.add(ValueDataEntry("General", generalClicks))
        data.add(ValueDataEntry("Entertainment", entertainmentClicks))
        data.add(ValueDataEntry("Health", healthClicks))
        data.add(ValueDataEntry("Science", scienceClicks))
        data.add(ValueDataEntry("Sports", sportsClicks))
        data.add(ValueDataEntry("Technology", technologyClicks))
        pie.data(data)
        chartView.setChart(pie)
        val totalClicks = businessClicks!! + generalClicks!! + entertainmentClicks!! + healthClicks!! + scienceClicks!! + sportsClicks!! + technologyClicks!!
        totalClicksText.text = totalClicks.toString()
        val editor = preferences.edit()
        editor.putInt("totalClicks", totalClicks)
        editor.apply()


        buttonLogOut.setOnClickListener {
            if (auth.currentUser!=null) {
                auth.signOut()
                val intent = Intent(activity, RegisterActivity::class.java)
                startActivity(intent)
                Toast.makeText(context,"The user is logged out", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.account_options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id==R.id.addFeeds) {
            childFragmentManager.beginTransaction().add(
                R.id.parent_account, AddFeedsFragment()).commit()
            llaout.visibility= View.GONE
        }
        if (id==R.id.feeds) {
            childFragmentManager.beginTransaction().add(
                    R.id.parent_account, FeedsFragment()).commit()
            llaout.visibility= View.GONE
        }

        if (id==R.id.topics) {
            childFragmentManager.beginTransaction().add(
                    R.id.parent_account, TopicsFragment()).commit()
            llaout.visibility= View.GONE
        }
        return super.onOptionsItemSelected(item)
    }

}

/*val textView: TextView = root.findViewById(R.id.text_account)
accountViewModel.text.observe(viewLifecycleOwner, Observer {
    textView.text = it
})*/