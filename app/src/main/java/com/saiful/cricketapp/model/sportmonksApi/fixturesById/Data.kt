package com.saiful.cricketapp.model.sportmonksApi.fixturesById

data class Data(
    val id: Int,
    val visitorteam_id: Int,
    val localteam_id: Int,
    val balls: List<Ball>,
    val lineup: List<Lineup>,
    val batting: List<Batting>,
    val bowling: List<Bowling>
)