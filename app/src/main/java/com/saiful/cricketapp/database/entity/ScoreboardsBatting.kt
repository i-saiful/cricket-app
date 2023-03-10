package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scoreboards_batting")
data class ScoreboardsBatting(
    @PrimaryKey val id: Int,
    val fixture_id: Int,
    val team_id: Int,
    val player_id: Int,
    val ball: Int,
    val batsman_name: String,
    val four_x: Int,
    val score: Int,
    val six_x: Int,
    val rate: Double
)
