package com.dhilasadrah.kadesubmission4.presenter

import com.dhilasadrah.kadesubmission4.coroutine.CoroutineContextProvider
import com.dhilasadrah.kadesubmission4.api.ApiRepository
import com.dhilasadrah.kadesubmission4.api.TheSportDBApi
import com.dhilasadrah.kadesubmission4.model.MatchResponse
import com.dhilasadrah.kadesubmission4.view.MatchView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(private val view: MatchView, private val apiRepository: ApiRepository, private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getPreviousMatch(idLeague: String?) {
        view.showLoading()
        GlobalScope.launch (context.main) {
            val data =  gson.fromJson(
                apiRepository.doRequestAsync(TheSportDBApi.getPastMatch(idLeague)).await(),
                MatchResponse::class.java
            )

            view.showMatchList(data.events)
            view.hideLoading()
        }
    }

    fun getNextMatch(idLeague: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(TheSportDBApi.getNextMatch(idLeague)).await(),
                MatchResponse::class.java
            )

            view.showMatchList(data.events)
                view.hideLoading()
            }
        }
    }
