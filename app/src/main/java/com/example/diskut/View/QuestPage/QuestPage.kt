package com.example.diskut.View.QuestPage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diskut.Model.User
import com.example.diskut.Model.UserType
import com.example.diskut.test_quest
import com.example.diskut.test_users
import com.example.diskut.ui.theme.AppTheme


@Composable
fun QuestPage(leaderboard: List<User>, questList: List<Quest>) {
    Column (modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Spacer(modifier = Modifier.height(24.dp))

        Leaderboard(leaderboard = leaderboard)

        Spacer(modifier = Modifier.height(24.dp))

        QuestList(questList = questList)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuestPagePreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            QuestPage(test_users, test_quest)
        }
    }
}




