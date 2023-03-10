package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scoreboards_bowling")
data class ScoreboardsBowling(
    @PrimaryKey val id: Int,
    val fixture_id: Int,
    val team_id: Int,
    val player_id: Int,
    val bowler_name: String,
    val medians: Int,
    val noball: Int,
    val overs: Double,
    val rate: Double,
    val runs: Int,
    val wickets: Int,
    val wide: Int
)
