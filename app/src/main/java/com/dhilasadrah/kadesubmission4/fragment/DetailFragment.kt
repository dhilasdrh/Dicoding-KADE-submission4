package com.dhilasadrah.kadesubmission4.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dhilasadrah.kadesubmission4.R
import com.dhilasadrah.kadesubmission4.api.ApiRepository
import com.dhilasadrah.kadesubmission4.model.LeagueDetail
import com.dhilasadrah.kadesubmission4.presenter.LeagueDetailPresenter
import com.dhilasadrah.kadesubmission4.util.invisible
import com.dhilasadrah.kadesubmission4.util.visible
import com.dhilasadrah.kadesubmission4.view.LeagueDetailView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), LeagueDetailView {

    companion object {
        const val LEAGUE_ID = "league_id"
        const val LEAGUE_NAME = "league_name"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idLeague = activity?.intent?.getStringExtra(LEAGUE_ID)
        val leagueName = activity?.intent?.getStringExtra(LEAGUE_NAME)
        if (idLeague != null) {
            val presenter = LeagueDetailPresenter(this, ApiRepository(), Gson())
            presenter.getLeagueDetail(idLeague)
        }

        val actionBar: ActionBar? = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.title = leagueName
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showLoading() {
        progressBarDetail.visible()
    }

    override fun hideLoading() {
        progressBarDetail.invisible()
    }

    override fun showLeagueDetail(data: LeagueDetail) {
        imgEmpty.invisible()
        Picasso.get().load(data.leagueFanart).into(league_banner)
        Picasso.get().load(data.leaguePoster).into(league_badge)
        league_name.text = data.leagueName
        league_formedYear.text = data.formedYear
        league_country.text = data.country
        league_description.text = data.description
    }
}