package com.dhilasadrah.kadesubmission4.view

import com.dhilasadrah.kadesubmission4.model.MatchDetail
import com.dhilasadrah.kadesubmission4.model.Team

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: MatchDetail)
    fun showTeamBadge(team: Team, isHomeTeam: Boolean)
}