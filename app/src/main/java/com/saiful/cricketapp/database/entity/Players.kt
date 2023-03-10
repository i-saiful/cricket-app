package com.saiful.cricketapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Players(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "image_path") val imagePath: String,
    @ColumnInfo(name = "batting_style") val battingStyle: String?,
    @ColumnInfo(name = "bowling_style") val bowlingStyle: String?,
    @ColumnInfo(name = "position_name") val positionName: String,
    @ColumnInfo(name = "date_of_birth") val dateOfBirth: String,
    @ColumnInfo(name = "country_id") val country_id: Int,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
)