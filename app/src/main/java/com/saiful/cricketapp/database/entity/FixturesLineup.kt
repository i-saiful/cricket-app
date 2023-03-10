package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "fixtures_lineup",
    indices = [Index(value = ["fixtures_id", "team_id", "players_id"], unique = true)]
)
data class FixturesLineup(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val fixtures_id: String,
    val team_id: String,
    val players_id: String,
    val players_name: String,
    val players_position: String,
    val players_image: String
)
