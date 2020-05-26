package com.dhilasadrah.kadesubmission4.view

import com.dhilasadrah.kadesubmission4.model.Match

interface MatchSearchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchSearchResult(data: List<Match>)
    fun showEmpty()
}