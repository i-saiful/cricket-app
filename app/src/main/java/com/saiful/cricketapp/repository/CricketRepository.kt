package com.saiful.cricketapp.repository

import android.util.Log
import com.saiful.cricketapp.database.CricketDatabase
import com.saiful.cricketapp.database.entity.*
import com.saiful.cricketapp.network.CricketApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CricketRepository(private val database: CricketDatabase) {
    // team
    fun getAllTeams() = database.getCricketDao().getAllTeams()
    fun getTeamsCount() = database.getCricketDao().getTeamsCount()

    suspend fun fetchTeams() {
        withContext(Dispatchers.IO) {
            val teams = CricketApi.retrofitService.teams().data
            database.getCricketDao().insertAllTeams(teams)
        }
    }

    // team ranking
    fun getTeamsRanking(type: String, gender: String) =
        database.getCricketDao().getTeamsRanking(type, gender)

    suspend fun fetchTeamsRanking() {
        withContext(Dispatchers.IO) {
            val teams = CricketApi.retrofitService.teamRanking().data
            val rankingList = mutableListOf<Ranking>()
            for (ranking in teams) {
                for (team in ranking.team) {
                    rankingList.add(
                        Ranking(
                            type = ranking.type,
                            gender = ranking.gender,
                            teamId = team.id,
                            name = team.name,
                            image_path = team.image_path,
                            position = team.ranking.position,
                            matches = team.ranking.matches,
                            points = team.ranking.points,
                            rating = team.ranking.rating
                        )
                    )
                }
            }
            database.getCricketDao().insertTeamsRanking(rankingList)
        }
    }

    // players
    fun getPlayers() = database.getCricketDao().getPlayers()
    fun getPlayerCountry(playerId: String) = database.getCricketDao().getPlayerCountry(playerId)

    suspend fun fetchPlayers(lastNameSearch: String) {
        withContext(Dispatchers.IO) {
            val players = CricketApi.retrofitService.players(lastNameSearch).data
            Log.d("TAG", "last name search: ${players.size}")
            val playerList = mutableListOf<Players>()
            players.map {
                playerList.add(
                    Players(
                        id = it.id,
                        firstName = it.firstname,
                        lastName = it.lastname,
                        fullName = it.fullname,
                        gender = it.gender,
                        imagePath = it.image_path,
                        battingStyle = it.battingstyle,
                        bowlingStyle = it.bowlingstyle,
                        positionName = it.position.name,
                        country_id = it.country_id,
                        dateOfBirth = it.dateofbirth,
                        updatedAt = it.updated_at
                    )
                )
            }
            database.getCricketDao().insertPlayers(playerList)
        }
    }

    // country
    fun getCountry(id: String) = database.getCricketDao().getCountry(id)

    suspend fun fetchCountry(countryId: Int) {
        withContext(Dispatchers.IO) {
            val country = CricketApi.retrofitService.countryById(countryId).data
            database.getCricketDao().insertCountry(country)
        }
    }

    // join operation country and player
    fun getPlayerInfo(id: String) = database.getCricketDao().getPlayerInfo(id)

    // player career
    suspend fun fetchPlayerCareer(playerId: String) {
        withContext(Dispatchers.IO) {
            val playerCareer = CricketApi.retrofitService.playerCareer(playerId).data
            val bowling = mutableListOf<Bowling>()
            val batting = mutableListOf<Batting>()
//            Log.d("TAG", "fetch player career: $playerCareer")
            playerCareer.career.map {
                val bowlingCareer = it.bowling
                if (bowlingCareer != null) {
                    bowling.add(
                        Bowling(
                            playerId = it.player_id,
                            seasonId = it.season_id,
                            careerType = it.type,
                            average = bowlingCareer.average,
                            econRate = bowlingCareer.econ_rate,
                            fiveWickets = bowlingCareer.five_wickets,
                            fourWickets = bowlingCareer.four_wickets,
                            innings = bowlingCareer.innings,
                            matches = bowlingCareer.matches,
                            medians = bowlingCareer.medians,
                            noball = bowlingCareer.noball,
                            overs = bowlingCareer.overs,
                            rate = bowlingCareer.rate,
                            runs = bowlingCareer.runs,
                            strikeRate = bowlingCareer.strike_rate,
                            tenWickets = bowlingCareer.ten_wickets,
                            wickets = bowlingCareer.wickets,
                            wide = bowlingCareer.wide
                        )
                    )
                }

                val battingCareer = it.batting
                if (battingCareer != null) {
                    batting.add(
                        Batting(
                            playerId = it.player_id,
                            seasonId = it.season_id,
                            careerType = it.type,
                            average = battingCareer.average,
                            ballsFaced = battingCareer.balls_faced,
                            fifties = battingCareer.fifties,
                            fourX = battingCareer.four_x,
                            fowBalls = battingCareer.fow_balls,
                            fowScore = battingCareer.fow_score,
                            highestInningScore = battingCareer.highest_inning_score,
                            hundreds = battingCareer.hundreds,
                            innings = battingCareer.innings,
                            matches = battingCareer.matches,
                            notOuts = battingCareer.not_outs,
                            runsScored = battingCareer.runs_scored,
                            sixX = battingCareer.six_x,
                            strikeRate = battingCareer.strike_rate ?: 0.0
                        )
                    )
                }
            }
            Log.d("TAG", "bowling list: ${bowling.size}");
            Log.d("TAG", "batting list: ${batting.size}")
            database.getCricketDao().insertBowlingCareer(bowling)
            database.getCricketDao().insertBattingCareer(batting)
        }
    }

    fun getPlayerBattingCareer(id: String) = database.getCricketDao().getPlayerBattingCareer(id)
    fun getPlayerCareer(id: String) = database.getCricketDao().getPlayerCareer(id)
    fun getPlayerBowlingCareer(id: String) = database.getCricketDao().getPlayerBowlingCareer(id)

    // fixtures
    suspend fun fetchFixtures() {
        withContext(Dispatchers.IO) {
            var countFixtures = 0
            var fixtures = CricketApi.retrofitService.fixtures()
            countFixtures += fixtures.data.size

            var fixturesList = fixtures.data
            val runsList = mutableListOf<Runs>()
            fixtures.data.map {
                it.runs?.let { run ->
                    runsList.addAll(run)
                }
            }
            Log.d("TAG", "total fixtures list 1: ${fixturesList.size}");

            val countLink = fixtures.meta.links.size - 2
            for (i in 2..countLink) {
                fixtures = CricketApi.retrofitService.fixtures(i)
                countFixtures += fixtures.data.size
                fixturesList += fixtures.data
                fixtures.data.map {
                    it.runs?.let { run ->
                        runsList.addAll(run)
                    }
                }
            }

            Log.d("TAG", "total fixtures list: ${fixturesList.size}");
            Log.d("TAG", "total fixtures: $countFixtures");
            Log.d("TAG", "total runs list: ${runsList.size}");
            Log.d(
                "TAG",
                "total fixtures == Count Fixtures: ${countFixtures == fixtures.meta.total}"
            );
            database.getCricketDao().insertFixtures(fixturesList)
            database.getCricketDao().insertAllRuns(runsList)
        }
    }

    fun getCountriesCount() = database.getCricketDao().getCountriesCount()
    suspend fun fetchCountries() {
        withContext(Dispatchers.IO) {
            val countries = CricketApi.retrofitService.countries().data
            Log.d("TAG", "countries: ${countries.size}")
            database.getCricketDao().insertCountries(countries)
        }
    }

    fun getVenuesCount() = database.getCricketDao().getVenuesCount()
    suspend fun fetchVenues() {
        withContext(Dispatchers.IO) {
            val venues = CricketApi.retrofitService.venues().data
            Log.d("TAG", "venues: ${venues.size}")
            database.getCricketDao().insertAllVenues(venues)
        }
    }

    fun getStagesCount() = database.getCricketDao().getStagesCount()
    suspend fun fetchStages() {
        withContext(Dispatchers.IO) {
            val stages = CricketApi.retrofitService.stages().data
            Log.d("TAG", "stages: ${stages.size}")
            database.getCricketDao().insertAllStages(stages)
        }
    }

    fun getLeaguesCount() = database.getCricketDao().getLeaguesCount()
    suspend fun fetchLeagues() {
        withContext(Dispatchers.IO) {
            val leagues = CricketApi.retrofitService.leagues().data
            Log.d("TAG", "leagues: ${leagues.size}")
            database.getCricketDao().insertAllLeagues(leagues)
        }
    }

    fun getSeasonsCount() = database.getCricketDao().getSeasonsCount()
    suspend fun fetchSeasons() {
        withContext(Dispatchers.IO) {
            val seasons = CricketApi.retrofitService.seasons().data
            Log.d("TAG", "seasons: ${seasons.size}")
            database.getCricketDao().insertAllSeason(seasons)
        }
    }

    fun fixturesCount() = database.getCricketDao().getFixturesCount()
    fun fixtureMatches(status: String) =
        database.getCricketDao().getFixtureMatches(status)

    suspend fun fetchFixturesById(fixturesId: String) {
        Log.d("TAG", "fetch fixtures data: fixtures_id $fixturesId");
        withContext(Dispatchers.IO) {
            val fixture = CricketApi.retrofitService.fixturesById(fixturesId).data

            // fixtures lineup
            val fixturesLineup = mutableListOf<FixturesLineup>()
            fixture.lineup.forEach { player ->
                val lineup = player.lineup
                fixturesLineup.add(
                    FixturesLineup(
                        fixtures_id = fixture.id.toString(),
                        team_id = lineup?.team_id.toString(),
                        players_id = player.id.toString(),
                        players_name = player.fullname.toString(),
                        players_position = player.position?.name ?: "N/A",
                        players_image = player.image_path ?: "-"
                    )
                )
            }
            Log.d("TAG", "fixtures lineup: ${fixturesLineup.size}")
            database.getCricketDao().insertAllFixturesLineup(fixturesLineup);

            // fixture balls
            val balls = mutableListOf<Balls>()
            fixture.balls.map {
                balls.add(
                    Balls(
                        it.fixture_id.toString(),
                        it.id.toString(),
                        it.team_id.toString(),
                        it.ball.toString(),
                        it.batsman?.fullname.toString(),
                        it.bowler?.fullname.toString(),
                        it.score?.name.toString()
                    )
                )
            }
            Log.d("TAG", "ball count: ${balls.size}")
            database.getCricketDao().insertAllBalls(balls)

            // fixture player
            val playerList = mutableListOf<Players>()
            fixture.lineup.map {
                playerList.add(
                    Players(
                        id = it.id ?: 1,
                        firstName = it.firstname.toString(),
                        lastName = it.lastname.toString(),
                        fullName = it.fullname.toString(),
                        gender = it.gender.toString(),
                        imagePath = it.image_path.toString(),
                        battingStyle = it.battingstyle.toString(),
                        bowlingStyle = it.bowlingstyle.toString(),
                        positionName = it.position?.name.toString(),
                        country_id = it.country_id ?: 1,
                        dateOfBirth = it.dateofbirth.toString(),
                        updatedAt = it.updated_at.toString()
                    )
                )
            }
            database.getCricketDao().insertPlayers(playerList)

            // fixture batting scoreboard
            val scoreboardsBatting = mutableListOf<ScoreboardsBatting>()
            fixture.batting.map {
                scoreboardsBatting.add(
                    ScoreboardsBatting(
                        id = it.id,
                        fixture_id = it.fixture_id,
                        team_id = it.team_id,
                        player_id = it.player_id,
                        ball = it.ball,
                        batsman_name = it.batsman.fullname,
                        four_x = it.four_x,
                        score = it.score,
                        six_x = it.six_x,
                        rate = it.rate
                    )
                )
            }
            Log.d("TAG", "scoreboard batting size: ${scoreboardsBatting.size}")
            database.getCricketDao().insertScoreboardsBatting(scoreboardsBatting)

            // fixture bowling scoreboard
            val scoreboardsBowling = mutableListOf<ScoreboardsBowling>()
            fixture.bowling.map {
                scoreboardsBowling.add(
                    ScoreboardsBowling(
                        id = it.id,
                        fixture_id = it.fixture_id,
                        team_id = it.team_id,
                        player_id = it.player_id,
                        bowler_name = it.bowler.fullname,
                        medians = it.medians,
                        noball = it.noball,
                        overs = it.overs,
                        rate = it.rate,
                        runs = it.runs,
                        wickets = it.wickets,
                        wide = it.wide
                    )
                )
            }
            Log.d("TAG", "scoreboard batting size: ${scoreboardsBowling.size}")
            database.getCricketDao().insertScoreboardsBowling(scoreboardsBowling)
        }
    }

    fun fixturesLineupCount(fixtureId: String) =
        database.getCricketDao().getFixturesLineupCount(fixtureId)

    // recent match
    fun getRecentFixturesMatches() = database.getCricketDao().getRecentFixtureMatches()

    // fixtures match info
    fun getFixtureMatchInfo(fixtureId: String) =
        database.getCricketDao().getFixtureMatchInfo(fixtureId)

    // local team squad
    fun getLocalTeamSquad(fixtureId: String, teamId: String) =
        database.getCricketDao().getLocalTeamSquad(fixtureId, teamId)

    // visitor team squad
    fun getVisitorTeamSquad(fixtureId: String, teamId: String) =
        database.getCricketDao().getVisitorTeamSquad(fixtureId, teamId)

    // fixture balls
    fun getFixtureBalls(fixtureId: String, teamId: String) =
        database.getCricketDao().getFixtureBalls(fixtureId, teamId)

    // fixture
    fun getFixture(fixtureId: String) = database.getCricketDao().getFixture(fixtureId)

    // venue
    fun getVenue(venueId: String) = database.getCricketDao().getVenue(venueId)

    // officials
    fun getOfficialsCount() = database.getCricketDao().getOfficialsCount()
    suspend fun fetchOfficials() {
        withContext(Dispatchers.IO) {
            val official = CricketApi.retrofitService.officials().data
            Log.d("TAG", "fetch Officials: ${official.size}");
            database.getCricketDao().insertAllOfficials(official)
        }
    }

    // fixture umpire
    fun getFixtureUmpire(fixtureId: String) = database.getCricketDao().getFixtureUmpire(fixtureId)

    // leagues
    fun getLeagues() = database.getCricketDao().getLeagues()
    fun getLeaguesMatches(leagueId: String) = database.getCricketDao().getLeaguesMatches(leagueId)
    fun getFixturesByTeamId(teamId: String) = database.getCricketDao().getFixturesByTeamId(teamId)

    // scoreboards
    fun getScoreboardsBatting(fixtureId: String, teamId: String) =
        database.getCricketDao().getScoreboardsBatting(fixtureId, teamId)

    fun getScoreboardsBowling(fixtureId: String, teamId: String) =
        database.getCricketDao().getScoreboardsBowling(fixtureId, teamId)

    // fixture team vs team
    fun getFixtureTeamVsTeam(fixtureId: String) =
        database.getCricketDao().getFixtureTeamVsTeam(fixtureId)

    // ranking count
    fun getRankingCount() = database.getCricketDao().getRankingCount()

    // upcoming fixture
    fun getUpcomingFixture() = database.getCricketDao().getUpcomingFixture()
}