package com.saiful.cricketapp.model.sportmonksApi.fixturesById


data class Bowling(
    val bowler: Players,
    val fixture_id: Int,
    val id: Int,
    val medians: Int,
    val noball: Int,
    val overs: Double,
    val player_id: Int,
    val rate: Double,
    val runs: Int,
    val team_id: Int,
    val wickets: Int,
    val wide: Int
)