package com.saiful.cricketapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Teams(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "image_path") val image_path: String,
    @ColumnInfo(name = "country_id") val country_id: Int,
    @ColumnInfo(name = "national_team") val national_team: Boolean,
    @ColumnInfo(name = "updated_at") val updated_at: String
)