package com.s1aks.fastor.domain

import com.s1aks.fastor.data.entities.DataModel

interface Repository {
    suspend fun getData(word: String): List<DataModel>
    suspend fun saveToDB(word: String)
    suspend fun getHistoryList(): List<String>
    suspend fun deleteHistoryEntity(word: String)
    suspend fun clearHistory()
}
