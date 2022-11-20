package com.s1aks.fastor.data

import com.s1aks.fastor.data.api.ApiService
import com.s1aks.fastor.data.db.HistoryDataBase
import com.s1aks.fastor.data.db.HistoryEntity
import com.s1aks.fastor.domain.Repository
import com.s1aks.fastor.data.entities.DataModel

class RepositoryImpl : Repository {

    override suspend fun getData(word: String): List<DataModel> {
        return ApiService.getInstance().searchAsync(word).await()
    }

    override suspend fun saveToDB(word: String) {
        HistoryDataBase.getDaoInstance().insert(HistoryEntity(word))
    }

    override suspend fun getHistoryList(): List<String> {
        return HistoryDataBase.getDaoInstance().all().map { it.word }
    }
}