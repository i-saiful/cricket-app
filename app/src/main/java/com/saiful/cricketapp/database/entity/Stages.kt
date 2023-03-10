package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stages")
data class Stages(
    @PrimaryKey val id: Int,
    val league_id: Int,
    val season_id: Int,
    val name: String,
    val code: String,
    val type: String,
    val updated_at: String
)
