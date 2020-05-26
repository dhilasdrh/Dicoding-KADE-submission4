package com.dhilasadrah.kadesubmission4.api

import com.dhilasadrah.kadesubmission4.BuildConfig

object TheSportDBApi {
    fun getLeagueDetail(idLeague: String?): String? {
        return BuildConfig.BASE_URL + "api/v1/json/1/lookupleague.php?id=$idLeague"
    }

    fun getPastMatch(idLeague: String?): String? {
        return BuildConfig.BASE_URL + "api/v1/json/1/eventspastleague.php?id=$idLeague"
    }

    fun getNextMatch(idLeague: String?): String? {
        return BuildConfig.BASE_URL + "api/v1/json/1/eventsnextleague.php?id=$idLeague"
    }

    fun getMatchDetail(idLeague: String?): String? {
        return BuildConfig.BASE_URL + "api/v1/json/1/lookupevent.php?id=$idLeague"
    }

    fun getMatchSearch(query: String?): String? {
        return BuildConfig.BASE_URL + "api/v1/json/1/searchevents.php?e=$query"
    }

    fun getTeamDetail(idTeam: String?): String? {
        return BuildConfig.BASE_URL + "api/v1/json/1/lookupteam.php?id=$idTeam"
    }

}