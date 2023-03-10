package com.saiful.cricketapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saiful.cricketapp.database.entity.*

@Database(
    entities = [Teams::class, Ranking::class, Players::class, Countries::class, Batting::class,
        Bowling::class, Fixtures::class, Runs::class, Leagues::class, Season::class, Stages::class,
        Venues::class, FixturesLineup::class, Balls::class, Officials::class, ScoreboardsBatting::class,
        ScoreboardsBowling::class],
    version = 1,
    exportSchema = false
)
abstract class CricketDatabase : RoomDatabase() {
    abstract fun getCricketDao(): CricketDao

    companion object {
        @Volatile
        private var INSTANCE: CricketDatabase? = null
        fun getDatabase(context: Context): CricketDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CricketDatabase::class.java,
                    "cricket_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}