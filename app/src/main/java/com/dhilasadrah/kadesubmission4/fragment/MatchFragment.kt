package com.dhilasadrah.kadesubmission4.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.dhilasadrah.kadesubmission4.R
import com.dhilasadrah.kadesubmission4.activity.MatchSearchActivity
import com.dhilasadrah.kadesubmission4.adapter.MatchPagerAdapter
import com.dhilasadrah.kadesubmission4.fragment.DetailFragment.Companion.LEAGUE_ID
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.startActivity

class MatchFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_match, container, false)

        val viewPager: ViewPager = root.findViewById(R.id.viewPager)
        val tabLayout: TabLayout = root.findViewById(R.id.tabLayout)

        val idLeague = activity?.intent?.getStringExtra(LEAGUE_ID)
        val viewPagerAdapter = MatchPagerAdapter(context, idLeague, childFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        setHasOptionsMenu(true)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_menu -> {
                context?.startActivity<MatchSearchActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}