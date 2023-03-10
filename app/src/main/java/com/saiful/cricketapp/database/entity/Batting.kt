package com.saiful.cricketapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "batting",
    indices = [Index(value = ["player_id", "season_id", "career_type"], unique = true)]
)
data class Batting(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "player_id") val playerId: Int,
    @ColumnInfo(name = "season_id") val seasonId: Int,
    @ColumnInfo(name = "career_type") val careerType: String,
    val average: Double,
    @ColumnInfo(name = "balls_faced") val ballsFaced: Int,
    val fifties: Int,
    val fourX: Int,
    @ColumnInfo(name = "fow_balls") val fowBalls: Double,
    @ColumnInfo(name = "fow_score") val fowScore: Int,
    @ColumnInfo(name = "highest_inning_score") val highestInningScore: Int,
    val hundreds: Int,
    val innings: Int,
    val matches: Int,
    @ColumnInfo(name = "not_outs") val notOuts: Int,
    @ColumnInfo(name = "runs_scored") val runsScored: Int,
    val sixX: Int,
    @ColumnInfo(name = "strike_rate") val strikeRate: Double
)
