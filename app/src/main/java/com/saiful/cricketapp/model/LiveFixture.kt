package com.saiful.cricketapp.model

data class LiveFixture(
    val fixturesId: String,
    val stage: String,
    val localTeamImage: String,
    val localTeamCode: String,
    var localTeamScore: Int,
    val localTeamWickets: Int,
    val localTeamOvers: Double?,
    val visitorTeamImage: String,
    val visitorTeamCode: String,
    val visitorTeamScore: Int?,
    val visitorTeamWickets: Int?,
    val visitorTeamOvers: Double,
    val round: String,
    val note: String
)
