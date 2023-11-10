package com.example.diskut.View.MainPage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.diskut.ui.theme.AppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.diskut.ui.theme.AppTheme

@Composable
fun MainPage(goalPoints: Int, content_padding: Dp) {
    var currPoints by remember { mutableStateOf(270) }

    Column(
        modifier = Modifier.fillMaxSize().padding(content_padding),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        PointsIndicator(username = "Larry", currPoints = currPoints, goalPoints = goalPoints)

        ConversationCues(modifier = Modifier.weight(1.0f), text="You are both stupid")

        PeerDetails(username = "Jerry", occupation="Student - M23404")
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainPagePreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainPage(1000, 32.dp)
        }
    }
}