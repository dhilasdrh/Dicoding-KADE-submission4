package com.dhilasadrah.kadesubmission4.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dhilasadrah.kadesubmission4.R
import com.dhilasadrah.kadesubmission4.fragment.PreviousMatchFragment
import com.dhilasadrah.kadesubmission4.fragment.NextMatchFragment

class MatchPagerAdapter(private val mContext: Context?, private val idLeague: String?, fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    @StringRes
    private val tabTitle = intArrayOf(R.string.last_match, R.string.next_match)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = PreviousMatchFragment(idLeague)
            1 -> fragment = NextMatchFragment(idLeague)
        }
        return fragment as Fragment
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext?.resources?.getString(tabTitle[position])
    }
}