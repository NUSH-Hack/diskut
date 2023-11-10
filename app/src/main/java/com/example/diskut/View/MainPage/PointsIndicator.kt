package com.example.diskut.View.MainPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun PointsIndicator(
    modifier: Modifier = Modifier,
    username: String,
    currPoints: Int,
    goalPoints: Int,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier,
            text = username,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        Text(
            modifier = Modifier,
            text = "$currPoints/$goalPoints",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Start
        )

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(16.dp),
            progress = currPoints.toFloat() / goalPoints.toFloat(),
        )

    }
}