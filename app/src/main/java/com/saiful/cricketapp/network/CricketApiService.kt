package com.saiful.cricketapp.network

import com.saiful.cricketapp.model.sportmonksApi.*
import com.saiful.cricketapp.model.sportmonksApi.career.CareerResponse
import com.saiful.cricketapp.model.sportmonksApi.fixtures.FixturesResponse
import com.saiful.cricketapp.model.sportmonksApi.fixturesById.FixturesByIdResponse
import com.saiful.cricketapp.model.sportmonksApi.players.PlayersResponse
import com.saiful.cricketapp.model.sportmonksApi.ranking.RankingResponse
import com.saiful.cricketapp.util.Constant.Companion.BASE_URL
import com.saiful.cricketapp.util.Constant.Companion.CAREER
import com.saiful.cricketapp.util.Constant.Companion.FIXTURES_FIELDS
import com.saiful.cricketapp.util.Constant.Companion.RUNS
import com.saiful.cricketapp.util.Constant.Companion.TOKEN
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object CricketApi {
    val retrofitService: CricketApiService by lazy { retrofit.create(CricketApiService::class.java) }
}

interface CricketApiService {
    @GET("teams")
    suspend fun teams(
        @Query("api_token") apiKey: String = TOKEN
    ): TeamsResponse

    @GET("team-rankings")
    suspend fun teamRanking(
        @Query("api_token") apiKey: String = TOKEN
    ): RankingResponse

    @GET("players")
    suspend fun players(
        @Query("filter[lastname]") lastNameSearch: String,
        @Query("api_token") apiKey: String = TOKEN
    ): PlayersResponse

    @GET("countries/{COUNTRY_ID}")
    suspend fun countryById(
        @Path("COUNTRY_ID") countryId: Int,
        @Query("api_token") apiKey: String = TOKEN
    ): CountryResponse

    @GET("countries")
    suspend fun countries(
        @Query("api_token") apiKey: String = TOKEN
    ): CountriesResponse

    @GET("players/{PLAYER_ID}")
    suspend fun playerCareer(
        @Path("PLAYER_ID") playerId: String,
        @Query("include") career: String = CAREER,
        @Query("api_token") apiKey: String = TOKEN
    ): CareerResponse

    @GET("fixtures")
    suspend fun fixtures(
        @Query("page") pageNumber: Int = 1,
        @Query("fields[fixtures]") fields: String = FIXTURES_FIELDS,
        @Query("include") runs: String = RUNS,
        @Query("api_token") apiKey: String = TOKEN
    ): FixturesResponse

    @GET("fixtures/{FIXTURE_ID}")
    suspend fun fixturesById(
        @Path("FIXTURE_ID") fixturesId: String,
        @Query("include") runs: String = "lineup, balls, bowling.bowler, batting.batsman",
        @Query("api_token") apiKey: String = TOKEN
    ): FixturesByIdResponse

    @GET("seasons")
    suspend fun seasons(
        @Query("api_token") apiKey: String = TOKEN
    ): SeasonResponse

    @GET("leagues")
    suspend fun leagues(
        @Query("api_token") apiKey: String = TOKEN
    ): LeaguesResponse

    @GET("stages")
    suspend fun stages(
        @Query("api_token") apiKey: String = TOKEN
    ): StagesResponse

    @GET("venues")
    suspend fun venues(
        @Query("api_token") apiKey: String = TOKEN
    ): VenuesResponse

    @GET("officials")
    suspend fun officials(
        @Query("api_token") apiKey: String = TOKEN
    ): OfficialsResponse
}