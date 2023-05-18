package com.s1aks.fastor.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.s1aks.fastor.R
import com.s1aks.fastor.ui.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    viewModel.showHistoryIcon = true
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .padding(top = 2.dp),
    ) {
        SearchTextField(viewModel)
        FindList(navController, viewModel)
    }
}

@Composable
fun SearchTextField(viewModel: MainViewModel) {
    var showKeyboard by rememberSaveable { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    var text by rememberSaveable { mutableStateOf("") }
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                text = ""
                viewModel.getData(text)
                focusRequester.requestFocus()
            },
        ) {
            Icon(
                Icons.Default.Cancel,
                contentDescription = "",
                tint = Color.Gray
            )
        }
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = text,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        onValueChange = {
            text = it
            viewModel.getData(it)
        },
        label = { Text(stringResource(id = R.string.search_hint)) },
        trailingIcon = if (text.isNotBlank()) trailingIconView else null
    )
    Spacer(modifier = Modifier.height(4.dp))
    LaunchedEffect(focusRequester) {
        if (showKeyboard) {
            delay(200)
            focusRequester.requestFocus()
            showKeyboard = false
        }
    }
}

@Composable
fun FindList(navController: NavController, viewModel: MainViewModel) {
    LazyColumn {
        items(viewModel.searchResponse) { listItem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp)
                    .clickable {
                        viewModel.clickedData = listItem
                        viewModel.saveToHistory(listItem.text.toString())
                        navController.navigate("description")
                    },
                border = BorderStroke(1.dp, MaterialTheme.colors.primary)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 4.dp
                    )
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = listItem.text.toString(),
                    )
                    Text(text = listItem.meanings?.get(0)?.translation?.translation.toString())
                }
            }
        }
    }
}
