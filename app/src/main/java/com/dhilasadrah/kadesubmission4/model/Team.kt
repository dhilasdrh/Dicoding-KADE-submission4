package com.dhilasadrah.kadesubmission4.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("idTeam")
    var IdTeam: String? = null,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null
)