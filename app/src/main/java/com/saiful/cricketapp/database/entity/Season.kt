package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seasons")
data class Season(
    @PrimaryKey val id: Int,
    val league_id: Int,
    val name: String,
    val code: String,
    val updated_at: String
)
