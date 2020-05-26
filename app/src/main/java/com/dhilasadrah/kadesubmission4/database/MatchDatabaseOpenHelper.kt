package com.dhilasadrah.kadesubmission4.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.dhilasadrah.kadesubmission4.model.Favorite
import org.jetbrains.anko.db.*

class MatchDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: MatchDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): MatchDatabaseOpenHelper {
            if (instance == null) {
                instance = MatchDatabaseOpenHelper(context.applicationContext)
            }
            return instance as MatchDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.MATCH_ID to TEXT + UNIQUE,
            Favorite.MATCH_DATE to TEXT,
            Favorite.MATCH_TIME to TEXT,
            Favorite.HOME_TEAM_ID to TEXT,
            Favorite.HOME_TEAM_NAME to TEXT,
            Favorite.HOME_TEAM_SCORE to TEXT,
            Favorite.AWAY_TEAM_ID to TEXT,
            Favorite.AWAY_TEAM_NAME to TEXT,
            Favorite.AWAY_TEAM_SCORE to TEXT,
            Favorite.STATUS to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

val Context.database: MatchDatabaseOpenHelper
    get() = MatchDatabaseOpenHelper.getInstance(applicationContext)