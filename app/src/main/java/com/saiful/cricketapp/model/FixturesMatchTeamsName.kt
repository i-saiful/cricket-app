package com.saiful.cricketapp.model

data class FixturesMatchTeamsName(
    val fixturesId: Int,
    val localTeamId: String,
    val localTeamName: String,
    val visitorTeamId: String,
    val visitorTeamName: String,
    val tossWonTeamName: String?,
    val winnerTeamName: String?,
    val manOfMatchName: String?,
    val manOfSeriesName: String?
)
