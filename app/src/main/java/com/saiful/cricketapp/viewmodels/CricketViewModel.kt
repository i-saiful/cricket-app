package com.saiful.cricketapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.saiful.cricketapp.database.CricketDatabase.Companion.getDatabase
import com.saiful.cricketapp.database.entity.*
import com.saiful.cricketapp.model.*
import com.saiful.cricketapp.repository.CricketRepository
import com.saiful.cricketapp.util.Constant.Companion.MEN_GENDER
import com.saiful.cricketapp.util.Constant.Companion.TEST
import kotlinx.coroutines.launch

class CricketViewModel(application: Application) : AndroidViewModel(application) {
    private val cricketRepository = CricketRepository(getDatabase(application))

    val teams: LiveData<List<Teams>> = cricketRepository.getAllTeams()
    val teamsCount: LiveData<TeamCount> = cricketRepository.getTeamsCount()
    fun ranking(type: String = TEST, gender: String = MEN_GENDER): LiveData<List<Ranking>> =
        cricketRepository.getTeamsRanking(type, gender)

    val players: LiveData<List<PlayerName>> = cricketRepository.getPlayers()
    fun playerInfo(id: String): LiveData<PlayerInfo> = cricketRepository.getPlayerInfo(id)
    fun countryId(id: String): LiveData<List<CountryName>> = cricketRepository.getCountry(id)
    fun playerCountryId(playerId: String): LiveData<PlayerCountry> =
        cricketRepository.getPlayerCountry(playerId)

    fun playerBattingCareer(playerId: String): LiveData<List<BattingCareer>> =
        cricketRepository.getPlayerBattingCareer(playerId)

    fun getPlayerCareer(playerId: String): LiveData<FixturesCount> =
        cricketRepository.getPlayerCareer(playerId)

    fun playerBowlingCareer(playerId: String): LiveData<List<BowlingCareer>> =
        cricketRepository.getPlayerBowlingCareer(playerId)

    val fixturesCount: LiveData<FixturesCount> = cricketRepository.fixturesCount()
    fun fixtureMatches(status: String): LiveData<List<FixturesMatch>> =
        cricketRepository.fixtureMatches(status)

    val getRecentFixturesMatches: LiveData<List<FixturesMatch>> =
        cricketRepository.getRecentFixturesMatches()

    fun getFixtureMatchInfo(fixtureId: String): LiveData<FixturesMatchTeamsName> =
        cricketRepository.getFixtureMatchInfo(fixtureId)

    fun getLocalTeamSquad(fixtureId: String, teamId: String): LiveData<List<FixturesLineup>> =
        cricketRepository.getLocalTeamSquad(fixtureId, teamId)

    fun getVisitorTeamSquad(fixtureId: String, teamId: String): LiveData<List<FixturesLineup>> =
        cricketRepository.getVisitorTeamSquad(fixtureId, teamId)

    fun getFixtureBalls(fixtureId: String, teamId: String): LiveData<List<Balls>> =
        cricketRepository.getFixtureBalls(fixtureId, teamId)

    fun getFixture(fixtureId: String): LiveData<Fixtures> = cricketRepository.getFixture(fixtureId)
    fun getVenue(venueId: String): LiveData<Venues> = cricketRepository.getVenue(venueId)
    fun getFixtureUmpire(fixtureId: String): LiveData<Umpire> =
        cricketRepository.getFixtureUmpire(fixtureId)

    val getLeagues: LiveData<List<Leagues>> = cricketRepository.getLeagues()
    fun getLeaguesMatches(leagueId: String): LiveData<List<FixturesMatch>> =
        cricketRepository.getLeaguesMatches(leagueId)

    fun getFixturesByTeamId(teamId: String): LiveData<List<FixturesMatch>> =
        cricketRepository.getFixturesByTeamId(teamId)

    // scoreboards
    fun getScoreboardsBatting(
        fixtureId: String,
        teamId: String
    ): LiveData<List<ScoreboardsBatting>> =
        cricketRepository.getScoreboardsBatting(fixtureId, teamId)

    fun getScoreboardsBowling(
        fixtureId: String,
        teamId: String
    ): LiveData<List<ScoreboardsBowling>> =
        cricketRepository.getScoreboardsBowling(fixtureId, teamId)

    // fixture team vs team
    fun getFixtureTeamVsTeam(fixtureId: String): LiveData<FixturesMatch> =
        cricketRepository.getFixtureTeamVsTeam(fixtureId)

    // check countries and fetch from internet
    val countriesSize: LiveData<CountData> = cricketRepository.getCountriesCount()
    fun fetchCountries() {
        viewModelScope.launch {
            try {
                cricketRepository.fetchCountries()
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchCountries networkError: $networkError")
            }
        }
    }

    // check venues and fetch from internet
    val venuesSize: LiveData<CountData> = cricketRepository.getVenuesCount()
    fun fetchVenues() {
        viewModelScope.launch {
            try {
                cricketRepository.fetchVenues()
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchVenues networkError: $networkError");
            }
        }
    }

    // check leagues and fetch from internet
    val leaguesSize: LiveData<CountData> = cricketRepository.getLeaguesCount()
    fun fetchLeagues() {
        viewModelScope.launch {
            try {
                cricketRepository.fetchLeagues()
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchLeagues networkError: $networkError");
            }
        }
    }

    // check seasons and fetch from internet
    val seasonsCount: LiveData<CountData> = cricketRepository.getSeasonsCount()
    fun fetchSeasons() {
        viewModelScope.launch {
            try {
                cricketRepository.fetchSeasons()
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchSeasons networkError: $networkError");
            }
        }
    }

    val stagesCount: LiveData<CountData> = cricketRepository.getStagesCount()
    fun fetchStages() {
        viewModelScope.launch {
            try {
                cricketRepository.fetchStages()
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchStages networkError: $networkError")
            }
        }
    }

    fun fetchTeams() {
        viewModelScope.launch {
            try {
                cricketRepository.fetchTeams()
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchTeams networkError: $networkError");
            }
        }
    }


    // check ranking and fetch from internet.
    val getRankingCount: LiveData<CountData> = cricketRepository.getRankingCount()
    fun fetchTeamsRanking() {
        viewModelScope.launch {
            try {
                cricketRepository.fetchTeamsRanking()
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchTeamsRanking networkError: $networkError");
            }
        }
    }

    fun fetchPlayers(playerLastName: String) {
        viewModelScope.launch {
            try {
                cricketRepository.fetchPlayers(playerLastName)
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchPlayers networkError: $networkError");
            }
        }
    }

    fun fetchCountryById(countryId: Int) {
        viewModelScope.launch {
            try {
                cricketRepository.fetchCountry(countryId)
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchCountryById networkError: $networkError");
            }
        }
    }

    fun fetchPlayerCareer(playerId: String) {
        viewModelScope.launch {
            try {
                cricketRepository.fetchPlayerCareer(playerId)
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchPlayerCareer networkError: $networkError");
            }
        }
    }

    fun fetchFixtures() {
        viewModelScope.launch {
            try {
                cricketRepository.fetchFixtures()
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchFixtures networkError: $networkError")
            }
        }
    }


    // check fixture lineup
    fun fixturesLineupCount(fixtureId: String): LiveData<CountData> =
        cricketRepository.fixturesLineupCount(fixtureId)

    fun fetchFixturesById(fixturesId: String) {
        viewModelScope.launch {
            try {
                cricketRepository.fetchFixturesById(fixturesId)
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchFixturesById networkError: $networkError")
            }
        }
    }

    // official
    val officialsCount: LiveData<CountData> = cricketRepository.getOfficialsCount()

    fun fetchOfficials() {
        viewModelScope.launch {
            try {
                cricketRepository.fetchOfficials()
            } catch (networkError: Exception) {
                Log.d("TAG", "fetchOfficials networkError: $networkError")
            }
        }
    }

    // upcoming fixture
    val getUpcomingFixture: LiveData<CountData> = cricketRepository.getUpcomingFixture()
}