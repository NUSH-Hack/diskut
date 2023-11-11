package com.example.diskut.View.QuestPage

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.outlinedCardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diskut.ui.theme.AppTheme

@Composable
internal fun QuestCard(quest: Quest, modifier: Modifier = Modifier) {
    OutlinedCard(modifier = modifier
        .fillMaxWidth()
        .height(80.dp)) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = quest.description,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${quest.completed}/${quest.goal}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuestCardPreview() {
    AppTheme {
        QuestCard(
            Quest(
                description = "Talk to 100 people",
                completed = 1,
                goal = 100
            )
        )
    }
}

@Composable
internal fun SpecialQuestCard(quest: Quest, modifier: Modifier = Modifier) {
    OutlinedCard(modifier = modifier
        .fillMaxWidth()
        .height(80.dp),
        colors = outlinedCardColors(

            containerColor = MaterialTheme.colorScheme.tertiaryContainer, // Set the background color here
            contentColor = MaterialTheme.colorScheme.onSurface // Set the content color based on the theme
        ),
        ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = quest.description,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${quest.completed}/${quest.goal}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}