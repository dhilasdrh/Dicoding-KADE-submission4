package com.dhilasadrah.kadesubmission4.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dhilasadrah.kadesubmission4.R
import com.dhilasadrah.kadesubmission4.fragment.FavoriteNextMatchFragment
import com.dhilasadrah.kadesubmission4.fragment.FavoritePreviousMatchFragment

class FavMatchPagerAdapter(private val mContext: Context?, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    @StringRes
    private val tabTitle = intArrayOf(R.string.last_match, R.string.next_match)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavoritePreviousMatchFragment()
            1 -> fragment = FavoriteNextMatchFragment()
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