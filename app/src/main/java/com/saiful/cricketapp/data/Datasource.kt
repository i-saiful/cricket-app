package com.saiful.cricketapp.data

import com.saiful.cricketapp.model.LiveFixture

class Datasource {

    fun loadLiveData(): List<LiveFixture> {
        return listOf(
            LiveFixture(
                fixturesId = "123",
                stage = "Twenty20 International",
                localTeamImage = "https://cdn.sportmonks.com/images/cricket/teams/10/10.png",
                localTeamCode = "IND",
                localTeamScore = 126,
                localTeamWickets = 9,
                localTeamOvers = 20.0,
                visitorTeamImage = "https://cdn.sportmonks.com/images/cricket/teams/5/37.png",
                visitorTeamCode = "BGD",
                visitorTeamScore = 0,
                visitorTeamWickets = 0,
                visitorTeamOvers = 0.0,
                round = "Final",
                note = ""
            )
        )
    }
}