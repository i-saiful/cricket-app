package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leagues")
data class Leagues(
    @PrimaryKey val id: Int,
    val season_id: Int,
    val country_id: Int,
    val name: String,
    val code: String,
    val image_path: String,
    val type: String,
    val updated_at: String
)
