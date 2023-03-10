package com.saiful.cricketapp.model

data class FixturesMatch(
    val fixtures_id: String,
    val stage: String,
    val venue_name: String,
    val venue_city: String,
    val localteam_name: String,
    val localteam_image: String,
    val localteam_code: String,
    val localteam_score: Int?,
    val localteam_wickets: Int?,
    val localteam_overs: Float?,
    val visitorteam_name: String,
    val visitorteam_image: String,
    val visitorteam_code: String,
    val visitorteam_score: Int?,
    val visitorteam_wickets: Int?,
    val visitorteam_overs: Float?,
    val round: String,
    val status: String,
    val note: String,
    val starting_at: String
)