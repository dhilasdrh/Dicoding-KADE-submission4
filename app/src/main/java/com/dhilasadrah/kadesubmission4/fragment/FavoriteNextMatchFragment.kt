package com.dhilasadrah.kadesubmission4.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.dhilasadrah.kadesubmission4.R
import com.dhilasadrah.kadesubmission4.activity.MatchDetailActivity
import com.dhilasadrah.kadesubmission4.activity.MatchDetailActivity.Companion.ID_AWAY_TEAM
import com.dhilasadrah.kadesubmission4.activity.MatchDetailActivity.Companion.ID_HOME_TEAM
import com.dhilasadrah.kadesubmission4.activity.MatchDetailActivity.Companion.ID_MATCH
import com.dhilasadrah.kadesubmission4.activity.MatchDetailActivity.Companion.MATCH_STATUS
import com.dhilasadrah.kadesubmission4.adapter.FavoriteMatchAdapter
import com.dhilasadrah.kadesubmission4.database.database
import com.dhilasadrah.kadesubmission4.util.*
import com.dhilasadrah.kadesubmission4.model.Favorite
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.startActivity

class FavoriteNextMatchFragment : Fragment(), AnkoComponent<Context> {
    private var favorites: MutableList<Favorite> = mutableListOf()
    private var isLastMatch: Boolean = false
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var rvFavNextMatch: RecyclerView
    private lateinit var imgEmpty: ImageView
    private lateinit var tvEmpty: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val actionBar: ActionBar? = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        return createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = FavoriteMatchAdapter(favorites){
            startActivity<MatchDetailActivity>(
                ID_MATCH to it.idMatch,
                ID_HOME_TEAM to it.idHomeTeam,
                ID_AWAY_TEAM to it.idAwayTeam,
                MATCH_STATUS to isLastMatch)
        }
        rvFavNextMatch.layoutManager = LinearLayoutManager(context)
        rvFavNextMatch.adapter = adapter

        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE).whereArgs("(STATUS = {status})",
                "status" to isLastMatch)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }

        if (favorites.isNullOrEmpty()) {
            imgEmpty.visible()
            imgEmpty.visible()
        } else {
            imgEmpty.invisible()
            tvEmpty.invisible()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)

            rvFavNextMatch = recyclerView {
                lparams(matchParent, wrapContent)
            }

            imgEmpty = imageView {
                id = R.id.noData
                imageResource = R.drawable.no_data
                visibility = View.VISIBLE
            }.lparams(dip(150), dip(150)) {
                centerInParent()
            }

            tvEmpty = textView {
                text = getString(R.string.no_favorites_yet)
                visibility = View.VISIBLE
            }.lparams(wrapContent, wrapContent) {
                centerInParent()
                below(R.id.noData)
            }
        }
    }
}
