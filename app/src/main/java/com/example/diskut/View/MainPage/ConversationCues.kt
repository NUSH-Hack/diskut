package com.example.diskut.View.MainPage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

@Composable
internal fun AnimatedConversationCues(
    modifier: Modifier = Modifier,
    conversationCues: SnapshotStateList<String>,
    expanded: Boolean
) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(conversationCues) {
        while (true) {
            delay(10000)
            currentIndex = if (conversationCues.isNotEmpty()) {
                (currentIndex + 1) % conversationCues.size // Loop back to the start of the list
            } else {
                0
            }
        }
    }

    val text = conversationCues.getOrNull(currentIndex) ?: ""

    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        ConversationCues(modifier = modifier, text = text)
    }
}

@Composable
internal fun ConversationCues(modifier: Modifier = Modifier, text: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
    }
}