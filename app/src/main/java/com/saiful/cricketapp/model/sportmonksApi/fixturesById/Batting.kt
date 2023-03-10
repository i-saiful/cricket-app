package com.saiful.cricketapp.model.sportmonksApi.fixturesById


data class Batting(
    val id: Int,
    val ball: Int,
    val batsman: Players,
    val fixture_id: Int,
    val four_x: Int,
    val player_id: Int,
    val rate: Double,
    val score: Int,
    val six_x: Int,
    val team_id: Int
)