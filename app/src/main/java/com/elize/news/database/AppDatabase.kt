package com.elize.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.elize.news.database.dao.NewsDAO
import com.elize.news.model.News

@Database(entities = [News::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val newsDAO: NewsDAO

    companion object {

        private lateinit var db: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (::db.isInitialized) return db

            db =

            return db
        }

    }

}