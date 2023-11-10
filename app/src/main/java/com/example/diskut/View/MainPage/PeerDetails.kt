package com.example.diskut.View.MainPage

import android.widget.ImageButton
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.diskut.R

@Composable
internal fun AnimatedPeerDetails(
    modifier: Modifier = Modifier,
    username: String,
    occupation: String,
    expanded: Boolean,
    onClick: () -> Unit
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = expanded,
        enter = slideInHorizontally {
            with(density) { 8.dp.roundToPx() }
        } + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        PeerDetails(modifier = modifier, username = username, occupation = occupation, onClick = onClick)
    }

}

@Composable
internal fun PeerDetails(
    modifier: Modifier = Modifier,
    username: String,
    occupation: String,
    onClick: () -> Unit
) {
    Row (modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically) {
        Column (horizontalAlignment = Alignment.End) {
            Text(
                text = username,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            Text(
                text = occupation,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Start
            )
        }
        
        Spacer(modifier = Modifier.width(4.dp))

        Image(
            modifier = Modifier
                .height(48.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .clickable {
                    onClick()
                },
            painter = painterResource(id = R.drawable.anon),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }

}
