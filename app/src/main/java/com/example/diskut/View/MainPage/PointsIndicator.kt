package com.example.diskut.View.MainPage

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun AnimatedPointsIndicator(
    modifier: Modifier = Modifier,
    username: String,
    currPoints: Int,
    goalPoints: Int,
    expanded: Boolean
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = if (expanded) Arrangement.Top else Arrangement.Center,
        horizontalAlignment = if (expanded) Alignment.Start else Alignment.CenterHorizontally
    ) {
        
        Text(
            modifier = Modifier.animateContentSize(),
            text = username,
            style = if (expanded) MaterialTheme.typography.titleMedium else MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        Text(
            modifier = Modifier.animateContentSize(),
            text = "$currPoints/$goalPoints",
            style = if (expanded) MaterialTheme.typography.labelSmall else MaterialTheme.typography.titleMedium,
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
