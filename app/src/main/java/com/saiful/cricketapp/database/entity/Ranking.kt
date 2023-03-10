package com.saiful.cricketapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "ranking", indices = [Index(value = ["type", "gender", "name"], unique = true)])
data class Ranking(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "team_id") var teamId: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "image_path") var image_path: String,
    @ColumnInfo(name = "position") var position: Int,
    @ColumnInfo(name = "matches") var matches: Int,
    @ColumnInfo(name = "points") var points: Int,
    @ColumnInfo(name = "rating") var rating: Int
)