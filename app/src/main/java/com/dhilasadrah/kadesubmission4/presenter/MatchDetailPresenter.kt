package com.dhilasadrah.kadesubmission4.presenter

import com.dhilasadrah.kadesubmission4.coroutine.CoroutineContextProvider
import com.dhilasadrah.kadesubmission4.api.ApiRepository
import com.dhilasadrah.kadesubmission4.api.TheSportDBApi
import com.dhilasadrah.kadesubmission4.model.MatchDetailResponse
import com.dhilasadrah.kadesubmission4.model.TeamResponse
import com.dhilasadrah.kadesubmission4.view.MatchDetailView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter(
    private val view: MatchDetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getMatchDetail(idEvent: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(TheSportDBApi.getMatchDetail(idEvent)).await(),
                MatchDetailResponse::class.java
            )

            view.showMatchDetail(data.events[0])
            view.hideLoading()
        }
    }

    fun getTeamBadge(idTeam: String?, isHomeTeam: Boolean = true) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(TheSportDBApi.getTeamDetail(idTeam)).await(),
                TeamResponse::class.java
            )

            view.showTeamBadge(data.teams[0], isHomeTeam)
            view.hideLoading()
        }
    }
}