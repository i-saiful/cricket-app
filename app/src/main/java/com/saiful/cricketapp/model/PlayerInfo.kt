package com.saiful.cricketapp.model

data class PlayerInfo(
    val fullName: String,
    val gender: String,
    val image_path: String,
    val batting_style: String?,
    val bowling_style: String?,
    val position_name: String,
    val date_of_birth: String,
    val countryName: String
)