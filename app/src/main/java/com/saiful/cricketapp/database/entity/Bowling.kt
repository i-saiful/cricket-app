package com.saiful.cricketapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bowling",
    indices = [Index(value = ["player_id", "season_id", "career_type"], unique = true)]
)
data class Bowling(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "player_id") val playerId: Int,
    @ColumnInfo(name = "season_id") val seasonId: Int,
    @ColumnInfo(name = "career_type") val careerType: String,
    val average: Double,
    @ColumnInfo(name = "econ_rate") val econRate: Double,
    @ColumnInfo(name = "five_wickets") val fiveWickets: Int,
    @ColumnInfo(name = "four_wickets") val fourWickets: Int,
    val innings: Int,
    val matches: Int,
    val medians: Int,
    @ColumnInfo(name = "no_ball") val noball: Int,
    val overs: Double,
    val rate: Double,
    val runs: Int,
    @ColumnInfo(name = "strike_rate") val strikeRate: Double,
    @ColumnInfo(name = "ten_wickets") val tenWickets: Int,
    val wickets: Int,
    val wide: Int
)