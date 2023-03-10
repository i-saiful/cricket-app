package com.saiful.cricketapp.model.sportmonksApi.career

data class Career(
    val batting: Batting?,
    val bowling: Bowling?,
    val player_id: Int,
    val resource: String,
    val season_id: Int,
    val type: String,
    val updated_at: String
)