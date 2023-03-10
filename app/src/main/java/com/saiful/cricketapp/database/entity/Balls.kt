package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balls")
data class Balls(
    val fixture_id: String,
    @PrimaryKey val balls_id: String,
    val team_id: String,
    val ball: String,
    val batsman_name: String,
    val bowler_name: String,
    val score: String
)
