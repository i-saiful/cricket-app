package com.saiful.cricketapp.util

class Constant {
    companion object {
        // Network Constant
        const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"
        const val TOKEN = "YygMvCRHZj1pZgJ6Wryy5YGM3pc43xYzilYfc0rXApNavy6KsaXUM5m5gjO6"
        const val FIXTURES_FIELDS =
            "id,league_id,season_id,stage_id,round,localteam_id,visitorteam_id,starting_at,type,status,note,venue_id,toss_won_team_id,winner_team_id,first_umpire_id,second_umpire_id,tv_umpire_id,referee_id,man_of_match_id,man_of_series_id,total_overs_played"

        // Message
        const val INTERNET_OFFLINE_MESSAGE = "Internet is not available."
        const val FETCH_FROM_INTERNET = "You requested has been successfully fetched."
        const val FETCH_FROM_INTERNET_ERROR =
            "We're sorry, there was an issue fetching the requested data."

        // Utils
        const val ODI = "ODI"
        const val TEST = "TEST"
        const val T20I = "T20I"
        const val MEN_GENDER = "men"
        const val WOMEN_GENDER = "women"
        const val RANKING_TYPE = "ranking_type"

        const val PLAYER_ID = "player_id"
        const val FIXTURES_ID = "fixtures_id"
        const val LOCAL_TEAM_ID = "local_team_id"
        const val VISITOR_TEAM_ID = "visitor_team_id"
        const val LEAGUE = "league_id"
        const val TEAM_ID = "team_id"

        const val CAREER = "career"

        const val LOCAL_TEAM = "localTeam"
        const val VISITOR_TEAM = "visitorTeam"

        // Matches
        const val MATCH_STATUS_FINISHED = "Finished"
        const val MATCH_STATUS_UPCOMING = "NS"
        const val MATCH_STATUS_ABANDONED = "Aban."
        const val RUNS = "runs"

        const val NOTIFY_USER = "notify_user"
    }
}