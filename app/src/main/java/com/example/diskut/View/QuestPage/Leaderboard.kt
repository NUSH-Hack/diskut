package com.example.diskut.View.QuestPage

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diskut.Controller.FetchLeaderboard
import com.example.diskut.User
import com.example.diskut.ui.theme.AppTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.json.JSONArray


@Composable
internal fun Leaderboard() {


}



@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LeaderboardPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            QuestPage(32.dp)
        }
    }
}
