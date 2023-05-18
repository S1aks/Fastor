package com.s1aks.fastor.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.s1aks.fastor.R
import com.s1aks.fastor.ui.screens.description.DescriptionScreen
import com.s1aks.fastor.ui.screens.history.HistoryScreen
import com.s1aks.fastor.ui.screens.main.MainScreen
import com.s1aks.fastor.ui.theme.FastorTheme

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FastorTheme {
                val navController = rememberNavController()
                val appName = stringResource(id = R.string.app_name)
                var topAppBarTitle by rememberSaveable { mutableStateOf(appName) }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(topAppBarTitle) },
                            actions = {
                                if (mainViewModel.showHistoryIcon) {
                                    IconButton(onClick = {
                                        navController.navigate("history")
                                    }) {
                                        Icon(
                                            Icons.Filled.History,
                                            contentDescription = getString(R.string.history_button_content_description)
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) {
                    if (mainViewModel.errorMessage.isBlank()) {
                        NavHost(navController = navController, startDestination = "main") {
                            composable("main") {
                                MainScreen(navController, mainViewModel)
                                topAppBarTitle = appName
                            }
                            composable("description") {
                                DescriptionScreen(mainViewModel)
                                topAppBarTitle =
                                    "$appName - ${stringResource(id = R.string.title_description)}"
                            }
                            composable("history") {
                                HistoryScreen(navController, mainViewModel)
                                topAppBarTitle =
                                    "$appName - ${stringResource(id = R.string.title_history)}"
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = getString(R.string.error_label_text),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = mainViewModel.errorMessage)
                            Spacer(modifier = Modifier.height(10.dp))
                            TextButton(onClick = {
                                mainViewModel.errorMessage = ""
                            }) {
                                Text(
                                    text = getString(R.string.action_retry_text),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}