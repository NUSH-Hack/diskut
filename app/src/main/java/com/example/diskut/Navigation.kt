package com.example.diskut

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diskut.Controller.fetchLeaderboard
import com.example.diskut.Model.User
import com.example.diskut.View.MainPage.MainPage
import com.example.diskut.View.Onboarding.OptionsPage
import com.example.diskut.View.QuestPage.QuestPage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NavigationHost(navController: NavHostController, bluetooth: Bluetooth) {
    var leaderboard by remember { mutableStateOf(listOf<User>()) }

    val coroutineScope = rememberCoroutineScope()

    val update: () -> Unit = {
        coroutineScope.launch {
            leaderboard = fetchLeaderboard()
        }
    }

    update()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainPage(goalPoints = 1000, bluetooth = bluetooth)
        }
        composable("quest") {
            QuestPage(leaderboard = leaderboard, questList = test_quest)
        }
        composable("options") {
            OptionsPage()
        }
    }
}

@Composable
fun MyNavigationBar(navController: NavController) {
    var selected by rememberSaveable {mutableStateOf(0)}

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Main") },
            label = { Text("Main") },
            selected = selected == 0, // you should control this based on navController's current route
            onClick = {
                navController.navigate("main")
                selected = 0
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Face, contentDescription = "Quest") },
            label = { Text("Quest") },
            selected = selected == 1, // you should control this based on navController's current route
            onClick = {
                navController.navigate("quest")
                selected = 1
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Options") },
            label = { Text("Options") },
            selected = selected == 2, // you should control this based on navController's current route
            onClick = {
                navController.navigate("options")
                selected = 2
            }
        )
    }
}
