package com.s1aks.fastor.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.s1aks.fastor.App

@Database(
    entities = [HistoryEntity::class],
    version = 2,
    exportSchema = false
)
abstract class HistoryDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        private var instance: HistoryDataBase? = null
        fun getInstance(): HistoryDataBase =
            instance ?: Room
                .databaseBuilder(App.appContext, HistoryDataBase::class.java, "HistoryDB")
                .fallbackToDestructiveMigration()
                .build()
    }
}