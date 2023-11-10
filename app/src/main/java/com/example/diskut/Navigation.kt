package com.example.diskut

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Main") },
            label = { Text("Main") },
            selected = true, // you should control this based on navController's current route
            onClick = {
                navController.navigate("main")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Face, contentDescription = "Quest") },
            label = { Text("Quest") },
            selected = false, // you should control this based on navController's current route
            onClick = {
                navController.navigate("quest")
            }
        )
    }
}
