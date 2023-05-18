package com.s1aks.fastor.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s1aks.fastor.data.RepositoryImpl
import com.s1aks.fastor.data.entities.DataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class MainViewModel : ViewModel() {
    private val repository = RepositoryImpl()
    var searchResponse: List<DataModel> by mutableStateOf(listOf())
    var showHistoryIcon: Boolean by mutableStateOf(true)
    var history: List<String> by mutableStateOf(listOf())
    var clickedData: DataModel? by mutableStateOf(null)
    var errorMessage: String by mutableStateOf("")

    fun getData(word: String) {
        cancelJob()
        if (word == "") {
            searchResponse = listOf()
            return
        }
        viewModelScope.launch {
            try {
                searchResponse = withContext(Dispatchers.IO) { repository.getData(word) }
            } catch (_: CancellationException) {
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getHistoryData(word: String) {
        cancelJob()
        viewModelScope.launch {
            try {
                clickedData = withContext(Dispatchers.IO) { repository.getData(word)[0] }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getHistory() {
        viewModelScope.launch {
            history = withContext(Dispatchers.IO) { repository.getHistoryList() }
        }
    }

    fun saveToHistory(word: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repository.saveToDB(word) }
        }
    }

    fun deleteFromHistory(word: String) {
        viewModelScope.launch {
            history = history.minus(word)
            withContext(Dispatchers.IO) { repository.deleteHistoryEntity(word) }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            history = listOf()
            withContext(Dispatchers.IO) { repository.clearHistory() }
        }
    }

    private fun cancelJob() {
        viewModelScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}
