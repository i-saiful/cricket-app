package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venues")
data class Venues(
    @PrimaryKey val id: Int,
    val country_id: Int,
    val name: String,
    val city: String,
    val image_path: String,
    val capacity: Int?,
    val floodlight: Boolean,
    val updated_at: String
)
