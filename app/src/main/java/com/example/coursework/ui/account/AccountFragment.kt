package com.example.coursework.ui.account

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.coursework.R
import com.example.coursework.ui.account.ui.FeedsFragment
import com.example.coursework.ui.account.ui.TopicsFragment
import com.example.coursework.ui.account.ui.UserFragment


class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        accountViewModel =
                ViewModelProvider(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)
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

        if (id==R.id.user) {
            childFragmentManager.beginTransaction().add(
                    R.id.fragment_account_container, UserFragment()).commit()
        }


        if (id==R.id.feeds) {
            childFragmentManager.beginTransaction().add(
                    R.id.fragment_account_container, FeedsFragment()).commit()
        }

        if (id==R.id.topics) {
            childFragmentManager.beginTransaction().add(
                    R.id.fragment_account_container, TopicsFragment()).commit()
        }
        return super.onOptionsItemSelected(item)
    }

}

/*val textView: TextView = root.findViewById(R.id.text_account)
accountViewModel.text.observe(viewLifecycleOwner, Observer {
    textView.text = it
})*/