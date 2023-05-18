package com.s1aks.fastor.ui.screens.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.s1aks.fastor.R
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
    val menuItems = listOf(
        MenuItem(stringResource(R.string.menu_item_delete)) { word ->
            viewModel.deleteFromHistory(
                word
            )
        },
        MenuItem(stringResource(R.string.menu_item_clear)) { viewModel.clearHistory() }
    )
    LazyColumn {
        items(viewModel.history) { historyItem ->
            HistoryItem(
                word = historyItem,
                dropDownItems = menuItems,
                onItemClick = {
                    viewModel.clickedData = null
                    viewModel.getHistoryData(historyItem)
                    navController.navigate("description")
                }
            )
        }
    }
}

data class MenuItem(val label: String, val action: (word: String) -> Unit)

@Composable
fun HistoryItem(
    word: String,
    modifier: Modifier = Modifier,
    dropDownItems: List<MenuItem>,
    onItemClick: () -> Unit
) {
    var isContextMenuVisible by rememberSaveable { mutableStateOf(false) }
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 2.dp)
            .clickable { onItemClick() }
            .onSizeChanged { itemHeight = it.height.dp }
            .pointerInput(true) {
                detectTapGestures(onLongPress = {
                    isContextMenuVisible = true
                    pressOffset = DpOffset(it.x.dp, it.y.dp)
                })
            },
        border = BorderStroke(1.dp, MaterialTheme.colors.primary)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold,
            text = word
        )
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(
                y = pressOffset.y - itemHeight
            )
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(onClick = {
                    item.action(word)
                    isContextMenuVisible = false
                }) {
                    Text(text = item.label)
                }
            }
        }
    }
}
