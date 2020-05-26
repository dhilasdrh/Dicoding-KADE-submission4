package com.dhilasadrah.kadesubmission4.view

import com.dhilasadrah.kadesubmission4.model.LeagueDetail

interface LeagueDetailView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetail(data: LeagueDetail)
}