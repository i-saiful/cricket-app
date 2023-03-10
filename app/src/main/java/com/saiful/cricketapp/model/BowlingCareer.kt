package com.saiful.cricketapp.model

data class BowlingCareer(
    val careerType: String,
    val average: Double,
    val econRate: Double,
    val fiveWickets: Int,
    val fourWickets: Int,
    val innings: Int,
    val matches: Int,
    val medians: Int,
    val noball: Int,
    val overs: Int,
    val rate: Double,
    val runs: Int,
    val strikeRate: Double,
    val tenWickets: Int,
    val wickets: Int,
    val wide: Int
)
