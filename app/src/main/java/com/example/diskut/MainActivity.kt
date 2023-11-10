package com.example.diskut

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.diskut.Controller.FetchLeaderboard
import com.example.diskut.Model.User
import com.example.diskut.Model.UserType
import com.example.diskut.View.QuestPage.Quest
import com.example.diskut.View.QuestPage.QuestPage
import com.example.diskut.ui.theme.AppTheme

val test_users: List<User> = listOf(
    User("", "Prannaya", UserType.STUDENT,"Year 6", 1000),
    User("", "Warren", UserType.STUDENT, "Year 4", 600),
    User("", "Mr. Meow", UserType.TEACHER, "Chemistry Department", 2)
)

val test_quest: List<Quest> = listOf<Quest>(
    Quest(
        description = "Talk to 100 people",
        completed = 1,
        goal = 100
    ),
    Quest(
        description = "Talk to 100 people",
        completed = 1,
        goal = 100
    ),
    Quest(
        description = "Talk to 100 people",
        completed = 1,
        goal = 100
    ),
    Quest(
        description = "Talk to 100 people",
        completed = 1,
        goal = 100
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val leaderboard = FetchLeaderboard()

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(leaderboard = leaderboard)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun App(leaderboard: List<User>) {
    Scaffold(
        bottomBar = {

        }
    ) { padding_values ->
        Box(modifier = Modifier.fillMaxSize().padding(padding_values)){
            QuestPage(leaderboard = leaderboard, questList = test_quest)
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppPreview() {
    AppTheme {
        App(leaderboard = test_q)
    }
}


private val test_q: List<User> = listOf(
    User("", "Prannaya", UserType.STUDENT,"Year 6", 1000),
    User("", "Warren", UserType.STUDENT, "Year 4", 600),
    User("", "Mr. Meow", UserType.TEACHER, "Chemistry Department", 2)
)