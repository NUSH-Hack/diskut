package com.example.diskut.View.MainPage

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.diskut.R

@Composable
internal fun PeerDetails(
    modifier: Modifier = Modifier,
    username: String,
    occupation: String,
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
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.anon),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }

}
