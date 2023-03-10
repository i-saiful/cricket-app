package com.saiful.cricketapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saiful.cricketapp.database.entity.*
import com.saiful.cricketapp.model.*

@Dao
interface CricketDao {
    //    team table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTeams(teams: List<Teams>)

    @Query("SELECT * FROM teams")
    fun getAllTeams(): LiveData<List<Teams>>

    @Query("SELECT COUNT(*) as count FROM teams")
    fun getTeamsCount(): LiveData<TeamCount>

    // ranking
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamsRanking(ranking: List<Ranking>)

    @Query("SELECT * FROM ranking WHERE type=:type AND gender=:gender ORDER BY position")
    fun getTeamsRanking(type: String, gender: String): LiveData<List<Ranking>>

    // Player
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(ranking: List<Players>)

    @Query("SELECT players.id AS id, players.full_name as fullName, players.image_path as imagePath, " +
            "countries.name as countryName FROM players " +
            "INNER JOIN countries ON players.country_id = countries.id")
    fun getPlayers(): LiveData<List<PlayerName>>

    @Query("SELECT country_id as countryId FROM players WHERE id=:playerId")
    fun getPlayerCountry(playerId: String): LiveData<PlayerCountry>

    // country
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countries: Countries)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<Countries>)

    @Query(
        "SELECT countries.id as id, countries.name FROM players " +
                "INNER JOIN countries ON countries.id = players.country_id " +
                "WHERE players.id =:playerId"
    )
    fun getCountry(playerId: String): LiveData<List<CountryName>>

    // join operation player and country
    @Query(
        "SELECT players.full_name AS fullName,players.gender, players.image_path," +
                "players.batting_style, players.bowling_style, players.position_name," +
                "players.date_of_birth, countries.name as countryName FROM players " +
                "INNER JOIN countries ON countries.id=players.country_id " +
                "WHERE players.id=:playerId"
    )
    fun getPlayerInfo(playerId: String): LiveData<PlayerInfo>

    // bowling
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBowlingCareer(bowling: List<Bowling>)

    @Query(
        "SELECT career_type AS careerType, " +
                "ROUND(AVG(average),2) AS average, " +
                "ROUND(AVG(econ_rate),2) AS econRate, " +
                "SUM(five_wickets) AS fiveWickets, " +
                "SUM(four_wickets) AS fourWickets, " +
                "SUM(innings) AS innings, " +
                "SUM(matches) AS matches, " +
                "SUM(medians) AS medians, " +
                "SUM(no_ball) AS noball, " +
                "SUM(overs) AS overs, " +
                "ROUND(AVG(rate),2) AS rate, " +
                "SUM(runs) AS runs, " +
                "ROUND(AVG(strike_rate),2) AS strikeRate, " +
                "SUM(ten_wickets) AS tenWickets, " +
                "SUM(wickets) AS wickets, " +
                "SUM(wide) AS wide " +
                "FROM bowling " +
                "WHERE player_id = :playerId " +
                "AND career_type IN ('ODI', 'Test/5day', 'T20I') " +
                "GROUP BY career_type"
    )
    fun getPlayerBowlingCareer(playerId: String): LiveData<List<BowlingCareer>>


    // batting
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBattingCareer(batting: List<Batting>)

    @Query(
        "SELECT career_type AS careerType, " +
                "ROUND(AVG(average), 2) AS average," +
                "SUM(balls_faced) AS ballsFaced," +
                "SUM(fifties) AS fifties," +
                "SUM(fourX) AS fourX," +
                "ROUND(AVG(fow_balls), 2) AS foWBalls," +
                "SUM(fow_score) AS foWScore," +
                "MAX(highest_inning_score) AS highestInningScore," +
                "SUM(hundreds) AS hundreds," +
                "SUM(innings) AS innings," +
                "SUM(matches) AS matches," +
                "SUM(not_outs) AS notOuts," +
                "SUM(runs_scored) AS runsScored," +
                "SUM(sixX) AS sixX," +
                "ROUND(AVG(strike_rate),2) AS strikeRate " +
                "FROM batting where player_id=:playerId " +
                "AND career_type IN ('ODI', 'Test/5day', 'T20I') " +
                "group by career_type"
    )
    fun getPlayerBattingCareer(playerId: String): LiveData<List<BattingCareer>>

    @Query("SELECT COUNT(*) AS count FROM batting WHERE player_id = :playerId")
    fun getPlayerCareer(playerId: String): LiveData<FixturesCount>

    // fixtures
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixtures(batting: List<Fixtures>)

    @Query("SELECT COUNT(*) AS count FROM fixtures")
    fun getFixturesCount(): LiveData<FixturesCount>

    @Query(
        "SELECT f.id AS fixtures_id, s.name AS stage, v.name AS venue_name, " +
                "v.city AS venue_city, l.name AS localteam_name, l.image_path AS localteam_image, " +
                "l.code AS localteam_code, r1.score AS localteam_score, r1.wickets AS " +
                "localteam_wickets, r1.overs AS localteam_overs, v1.name AS visitorteam_name, " +
                "v1.image_path AS visitorteam_image, v1.code AS visitorteam_code, r2.score AS " +
                "visitorteam_score, r2.wickets AS visitorteam_wickets, r2.overs AS visitorteam_overs, " +
                "f.round, f.status, f.note, f.starting_at FROM fixtures f JOIN stages s ON " +
                "s.id = f.stage_id JOIN venues v ON v.id = f.venue_id JOIN teams l ON " +
                "l.id = f.localteam_id JOIN teams v1 ON v1.id = f.visitorteam_id LEFT " +
                "JOIN runs r1 ON r1.fixture_id = f.id AND r1.team_id = f.localteam_id LEFT " +
                "JOIN runs r2 ON r2.fixture_id = f.id AND r2.team_id = f.visitorteam_id WHERE " +
                "f.status =:status"
    )
    fun getFixtureMatches(status: String): LiveData<List<FixturesMatch>>

    // runs
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRuns(runs: List<Runs>)

    // leagues
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLeagues(leagues: List<Leagues>)

    // Season
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSeason(season: List<Season>)

    // Stages
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStages(stages: List<Stages>)

    // Venues
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllVenues(venues: List<Venues>)

    // count
    @Query("SELECT COUNT(*) AS count FROM leagues")
    fun getLeaguesCount(): LiveData<CountData>

    @Query("SELECT COUNT(*) AS count FROM seasons")
    fun getSeasonsCount(): LiveData<CountData>

    @Query("SELECT COUNT(*) AS count FROM stages")
    fun getStagesCount(): LiveData<CountData>

    @Query("SELECT COUNT(*) AS count FROM venues")
    fun getVenuesCount(): LiveData<CountData>

    @Query("SELECT COUNT(*) AS count FROM countries")
    fun getCountriesCount(): LiveData<CountData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFixturesLineup(fixturesLineup: List<FixturesLineup>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBalls(balls: List<Balls>)

    @Query("SELECT COUNT(*) AS count FROM fixtures_lineup WHERE fixtures_id=:fixtureId")
    fun getFixturesLineupCount(fixtureId: String): LiveData<CountData>

    @Query("SELECT COUNT(*) AS count FROM ranking")
    fun getRankingCount(): LiveData<CountData>

    // recent match
    @Query(
        "SELECT f.id AS fixtures_id, s.name AS stage, v.name AS venue_name, v.city AS venue_city, " +
                "l.name AS localteam_name, l.image_path AS localteam_image, l.code AS localteam_code, " +
                "r1.score AS localteam_score, r1.wickets AS localteam_wickets, r1.overs AS localteam_overs, " +
                "v1.name AS visitorteam_name, v1.image_path AS visitorteam_image, " +
                "v1.code AS visitorteam_code, r2.score AS visitorteam_score, " +
                "r2.wickets AS visitorteam_wickets, r2.overs AS visitorteam_overs, " +
                "f.round, f.status, f.note, f.starting_at " +
                "FROM fixtures f " +
                "JOIN stages s ON s.id = f.stage_id " +
                "JOIN venues v ON v.id = f.venue_id " +
                "JOIN teams l ON l.id = f.localteam_id " +
                "JOIN teams v1 ON v1.id = f.visitorteam_id " +
                "LEFT JOIN runs r1 ON r1.fixture_id = f.id AND r1.team_id = f.localteam_id " +
                "LEFT JOIN runs r2 ON r2.fixture_id = f.id AND r2.team_id = f.visitorteam_id " +
                "WHERE f.starting_at <= datetime('now') " +
                "ORDER BY f.starting_at DESC LIMIT 6"
    )
    fun getRecentFixtureMatches(): LiveData<List<FixturesMatch>>

    @Query(
        "SELECT * FROM ( " +
                "    SELECT Fixtures.id AS fixturesId, " +
                "    Fixtures.localteam_id AS localTeamId, " +
                "    T1.name AS localTeamName, " +
                "    Fixtures.visitorteam_id AS visitorTeamId, " +
                "    T2.name AS visitorTeamName, " +
                "    T3.name AS tossWonTeamName, " +
                "    T4.name AS winnerTeamName, " +
                "M.full_name AS manOfMatchName, " +
                "S.full_name AS manOfSeriesName " +
                "FROM Fixtures " +
                "LEFT JOIN Teams AS T1 ON Fixtures.localteam_id=T1.id " +
                "LEFT JOIN Teams AS T2 ON Fixtures.visitorteam_id=T2.id " +
                "LEFT JOIN Teams AS T3 ON Fixtures.toss_won_team_id=T3.id " +
                "LEFT JOIN Teams AS T4 ON Fixtures.winner_team_id=T4.id " +
                "LEFT JOIN Players AS M ON Fixtures.man_of_match_id=M.id " +
                "LEFT JOIN Players AS S ON Fixtures.man_of_series_id=S.id " +
                "WHERE Fixtures.id=:fixtureId " +
                ") "
    )
    fun getFixtureMatchInfo(fixtureId: String): LiveData<FixturesMatchTeamsName>

    // local team squad
    @Query("select * from fixtures_lineup where fixtures_id=:fixtureId and team_id = :teamId")
    fun getLocalTeamSquad(fixtureId: String, teamId: String): LiveData<List<FixturesLineup>>

    // visitor team squad
    @Query("select * from fixtures_lineup where fixtures_id=:fixtureId and team_id = :teamId")
    fun getVisitorTeamSquad(fixtureId: String, teamId: String): LiveData<List<FixturesLineup>>

    @Query("SELECT * FROM balls WHERE fixture_id=:fixtureId AND team_id=:teamId ORDER BY team_id, balls_id DESC")
    fun getFixtureBalls(fixtureId: String, teamId: String): LiveData<List<Balls>>

    @Query("SELECT * FROM fixtures WHERE id=:fixtureId")
    fun getFixture(fixtureId: String): LiveData<Fixtures>

    @Query("SELECT * FROM venues WHERE id=:venueId")
    fun getVenue(venueId: String): LiveData<Venues>

    // official
    @Query("SELECT COUNT(*) AS count FROM officials")
    fun getOfficialsCount(): LiveData<CountData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOfficials(balls: List<Officials>)

    // fixture umpire
    @Query(
        "SELECT officials.fullname AS referee, " +
                "officials2.fullname AS first_umpire, " +
                "officials3.fullname AS second_umpire, " +
                "officials4.fullname AS tv_umpire " +
                "FROM Fixtures " +
                "LEFT JOIN Officials officials ON officials.id = Fixtures.referee_id " +
                "LEFT JOIN Officials officials2 ON officials2.id = Fixtures.first_umpire_id " +
                "LEFT JOIN Officials officials3 ON officials3.id = Fixtures.second_umpire_id " +
                "LEFT JOIN Officials officials4 ON officials4.id = Fixtures.tv_umpire_id " +
                "WHERE Fixtures.id = :fixtureId"
    )
    fun getFixtureUmpire(fixtureId: String): LiveData<Umpire>

    // series
    @Query("SELECT * FROM leagues")
    fun getLeagues(): LiveData<List<Leagues>>

    @Query("SELECT f.id AS fixtures_id, s.name AS stage, v.name AS venue_name, v.city AS venue_city, l.name AS localteam_name, l.image_path AS localteam_image, l.code AS localteam_code, r1.score AS localteam_score, r1.wickets AS localteam_wickets, r1.overs AS localteam_overs, v1.name AS visitorteam_name, v1.image_path AS visitorteam_image, v1.code AS visitorteam_code, r2.score AS visitorteam_score, r2.wickets AS visitorteam_wickets, r2.overs AS visitorteam_overs, f.round, f.status, f.note, f.starting_at FROM fixtures f JOIN stages s ON s.id = f.stage_id JOIN venues v ON v.id = f.venue_id JOIN teams l ON l.id = f.localteam_id JOIN teams v1 ON v1.id = f.visitorteam_id LEFT JOIN runs r1 ON r1.fixture_id = f.id AND r1.team_id = f.localteam_id LEFT JOIN runs r2 ON r2.fixture_id = f.id AND r2.team_id = f.visitorteam_id WHERE f.league_id = :leagueId ORDER BY f.starting_at DESC")
    fun getLeaguesMatches(leagueId: String): LiveData<List<FixturesMatch>>

    @Query("SELECT f.id AS fixtures_id, s.name AS stage, v.name AS venue_name, v.city AS venue_city, l.name AS localteam_name, l.image_path AS localteam_image, l.code AS localteam_code, r1.score AS localteam_score, r1.wickets AS localteam_wickets, r1.overs AS localteam_overs, v1.name AS visitorteam_name, v1.image_path AS visitorteam_image, v1.code AS visitorteam_code, r2.score AS visitorteam_score, r2.wickets AS visitorteam_wickets, r2.overs AS visitorteam_overs, f.round, f.status, f.note, f.starting_at FROM fixtures f JOIN stages s ON s.id = f.stage_id JOIN venues v ON v.id = f.venue_id JOIN teams l ON l.id = f.localteam_id JOIN teams v1 ON v1.id = f.visitorteam_id LEFT JOIN runs r1 ON r1.fixture_id = f.id AND r1.team_id = f.localteam_id LEFT JOIN runs r2 ON r2.fixture_id = f.id AND r2.team_id = f.visitorteam_id WHERE (f.localteam_id = :teamId OR f.visitorteam_id = :teamId)")
    fun getFixturesByTeamId(teamId: String): LiveData<List<FixturesMatch>>


    // scoreboards batting
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScoreboardsBatting(scoreboardsBatting: List<ScoreboardsBatting>)

    @Query("SELECT * FROM scoreboards_batting WHERE fixture_id=:fixtureId AND team_id=:teamId")
    fun getScoreboardsBatting(fixtureId: String, teamId: String): LiveData<List<ScoreboardsBatting>>

    // scoreboards bowling
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScoreboardsBowling(scoreboardsBowling: List<ScoreboardsBowling>)

    @Query("SELECT * FROM scoreboards_bowling WHERE fixture_id=:fixtureId AND team_id=:teamId")
    fun getScoreboardsBowling(fixtureId: String, teamId: String): LiveData<List<ScoreboardsBowling>>

    // fixture team vs team
    @Query(
        "SELECT f.id AS fixtures_id, s.name AS stage, v.name AS venue_name,v.city AS venue_city, " +
                "l.name AS localteam_name, l.image_path AS localteam_image, l.code AS localteam_code, " +
                "r1.score AS localteam_score, r1.wickets AS localteam_wickets, r1.overs AS " +
                "localteam_overs, v1.name AS visitorteam_name, v1.image_path AS visitorteam_image, " +
                "v1.code AS visitorteam_code, r2.score AS visitorteam_score, r2.wickets AS " +
                "visitorteam_wickets, r2.overs AS visitorteam_overs, f.round, f.status, f.note, " +
                "f.starting_at FROM fixtures f JOIN stages s ON s.id = f.stage_id JOIN venues v " +
                "ON v.id = f.venue_id JOIN teams l ON l.id = f.localteam_id JOIN teams v1 ON " +
                "v1.id = f.visitorteam_id LEFT JOIN runs r1 ON r1.fixture_id = f.id AND r1.team_id = " +
                "f.localteam_id LEFT JOIN runs r2 ON r2.fixture_id = f.id AND " +
                "r2.team_id = f.visitorteam_id WHERE f.id = :fixtureId"
    )
    fun getFixtureTeamVsTeam(fixtureId: String): LiveData<FixturesMatch>

    // upcoming fixture
    @Query("SELECT count(*) AS count FROM fixtures WHERE status='NS'")
    fun getUpcomingFixture(): LiveData<CountData>

}