package com.s1aks.fastor.data

import com.s1aks.fastor.data.api.ApiService
import com.s1aks.fastor.data.db.HistoryDataBase
import com.s1aks.fastor.data.db.HistoryEntity
import com.s1aks.fastor.data.entities.DataModel
import com.s1aks.fastor.domain.Repository

class RepositoryImpl : Repository {

    override suspend fun getData(word: String): List<DataModel> =
        ApiService.getInstance().searchAsync(word).await()

    override suspend fun saveToDB(word: String) {
        HistoryDataBase.getInstance().historyDao().insert(HistoryEntity(word))
    }

    override suspend fun getHistoryList(): List<String> =
        HistoryDataBase.getInstance().historyDao().all().map { it.word }

    override suspend fun deleteHistoryEntity(word: String) =
        HistoryDataBase.getInstance().historyDao().delete(HistoryEntity(word))

    override suspend fun clearHistory() {
        HistoryDataBase.getInstance().clearAllTables()
    }
}