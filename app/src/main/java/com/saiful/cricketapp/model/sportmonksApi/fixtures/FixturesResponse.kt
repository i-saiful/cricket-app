package com.saiful.cricketapp.model.sportmonksApi.fixtures

import com.saiful.cricketapp.database.entity.Fixtures

data class FixturesResponse(
    val `data`: List<Fixtures>,
    val meta: Meta
)