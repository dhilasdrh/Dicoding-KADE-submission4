package com.dhilasadrah.kadesubmission4.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dhilasadrah.kadesubmission4.R
import com.dhilasadrah.kadesubmission4.adapter.LeagueAdapter
import com.dhilasadrah.kadesubmission4.fragment.DetailFragment.Companion.LEAGUE_ID
import com.dhilasadrah.kadesubmission4.fragment.DetailFragment.Companion.LEAGUE_NAME
import com.dhilasadrah.kadesubmission4.model.League
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.wrapContent

class MainActivity : AppCompatActivity() {
    private var leagues: MutableList<League> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        recyclerView {
            id = R.id.rvLeagueList
            lparams(width = matchParent, height = wrapContent)
            layoutManager = GridLayoutManager(context, 2)
            adapter = LeagueAdapter(leagues) {
                    startActivity<LeagueDetailActivity>(LEAGUE_ID to it.id, LEAGUE_NAME to it.name)
                }
        }
    }

    private fun initData() {
        val image = resources.obtainTypedArray(R.array.league_img)
        val name = resources.getStringArray(R.array.league_name)
        val id = resources.getStringArray(R.array.league_id)

        leagues.clear()
        for (i in name.indices) {
            leagues.add(League(id[i], name[i], image.getResourceId(i, 0)))
        }
        image.recycle()
    }
}
