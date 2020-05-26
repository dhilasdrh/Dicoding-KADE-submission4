package com.dhilasadrah.kadesubmission4.view

import com.dhilasadrah.kadesubmission4.model.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Match>)
}