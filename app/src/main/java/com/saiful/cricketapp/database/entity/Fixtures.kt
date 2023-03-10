package com.saiful.cricketapp.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "fixtures")
data class Fixtures(
    @PrimaryKey var id: Int,
    var league_id: Int,
    var season_id: Int,
    var stage_id: Int,
    var venue_id: Int?,
    var localteam_id: Int,
    var visitorteam_id: Int,
    var winner_team_id: Int?,
    var man_of_match_id: Int?,
    var man_of_series_id: Int?,
    var note: String,
    var round: String,
    var starting_at: String,
    var status: String,
    var toss_won_team_id: Int?,
    var total_overs_played: Int?,
    var type: String,
    var referee_id: Int?,
    var first_umpire_id: Int?,
    var second_umpire_id: Int?,
    var tv_umpire_id: Int?,
    @Ignore val runs: List<Runs>?
) {
    constructor() : this(
        0,
        0,
        0,
        0,
        null,
        0,
        0,
        null,
        null,
        null,
        "",
        "",
        "",
        "",
        null,
        null,
        "",
        null,
        null,
        null,
        null,
        null
    )
}
