package com.example.diskut.View.QuestPage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.diskut.ui.theme.AppTheme

@Composable
fun QuestPage(content_padding: Dp) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(content_padding)
    ) {
        Leaderboard()
        QuestList()
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
            QuestPage(32.dp)
        }
    }
}


internal val q: List<Quest> = listOf<Quest>(
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
