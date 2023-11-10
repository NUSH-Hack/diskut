package com.example.diskut

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.diskut.View.MainPage.MainPage
import com.example.diskut.View.QuestPage.QuestPage

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainPage(goalPoints = 1000)
        }
        composable("quest") {
            QuestPage(leaderboard = test_users, questList = test_quest)
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
    }
}
