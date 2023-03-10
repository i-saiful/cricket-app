package com.saiful.cricketapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class Countries(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "continent_id") val continent_id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_path") val image_path: String,
    @ColumnInfo(name = "updated_at") val updated_at: String?
)
