package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "officials")
data class Officials(
    @PrimaryKey val id: Int,
    val country_id: Int,
    val dateofbirth: String,
    val firstname: String,
    val fullname: String,
    val gender: String,
    val lastname: String,
    val updated_at: String
)
