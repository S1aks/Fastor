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
        private var daoInstance: HistoryDao? = null
        fun getDaoInstance(): HistoryDao =
            daoInstance ?: Room
                .databaseBuilder(App.appContext, HistoryDataBase::class.java, "HistoryDB")
                .fallbackToDestructiveMigration()
                .build()
                .historyDao()
    }
}