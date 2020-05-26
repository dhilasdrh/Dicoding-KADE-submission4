package com.dhilasadrah.kadesubmission4.presenter

import com.dhilasadrah.kadesubmission4.coroutine.CoroutineContextProvider
import com.dhilasadrah.kadesubmission4.api.ApiRepository
import com.dhilasadrah.kadesubmission4.api.TheSportDBApi
import com.dhilasadrah.kadesubmission4.model.LeagueDetailResponse
import com.dhilasadrah.kadesubmission4.view.LeagueDetailView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailPresenter (private val view: LeagueDetailView,
                             private val apiRepository: ApiRepository,
                             private val gson: Gson,
                             private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getLeagueDetail(idLeague: String){
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(apiRepository.doRequestAsync(TheSportDBApi.getLeagueDetail(idLeague)).await(),
                    LeagueDetailResponse::class.java
                )

            view.showLeagueDetail(data.leagues[0])
            view.hideLoading()
        }
    }
}