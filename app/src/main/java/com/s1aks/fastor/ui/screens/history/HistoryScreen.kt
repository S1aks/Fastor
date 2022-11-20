package com.s1aks.fastor.ui.screens.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.s1aks.fastor.ui.MainViewModel

@Composable
fun HistoryScreen(navController: NavController, viewModel: MainViewModel) {
    viewModel.showHistoryIcon = false
    viewModel.getHistory()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(top = 4.dp),
    ) {
        HistoryList(navController, viewModel)
    }
}

@Composable
fun HistoryList(navController: NavController, viewModel: MainViewModel) {
    LazyColumn {
        items(viewModel.history) { listItem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .clickable {
                        viewModel.getHistoryData(listItem)
                        navController.navigate("description") {
                            popUpTo("history")
                        }
                    },
                border = BorderStroke(1.dp, MaterialTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    text = listItem
                )
            }
        }
    }
}
