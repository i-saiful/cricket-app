package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "runs")
data class Runs(
    @PrimaryKey val id: Int,
    val fixture_id: Int,
    val team_id: Int,
    val inning: Int,
    val score: Int,
    val wickets: Int,
    val overs: Double,
    val updated_at: String
)
