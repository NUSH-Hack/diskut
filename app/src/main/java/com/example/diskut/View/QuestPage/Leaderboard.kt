package com.example.diskut.View.QuestPage

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diskut.Model.User
import com.example.diskut.R
import com.example.diskut.ui.theme.AppTheme


@Composable
internal fun Leaderboard(leaderboard: List<User>, modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(0.35f), horizontalArrangement = Arrangement.SpaceEvenly) {
        // second place
        Place(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight(),
            user = leaderboard[1],
            painter = painterResource(id = R.drawable.medal_2),
            style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.weight(0.035f))

        // first place
        Place(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxHeight(),
            user = leaderboard[0],
            painter = painterResource(id = R.drawable.trophy_1),
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(modifier = Modifier.weight(0.035f))

        // third place
        Place(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight(),
            user = leaderboard[2],
            painter = painterResource(id = R.drawable.medal_3),
            style = MaterialTheme.typography.labelSmall
        )

    }
}

@Composable
private fun Place(user: User, painter: Painter, style: TextStyle, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            user.name,
            style = style,
            fontWeight = FontWeight.Bold
        )

        Text(
            "${user.points} pts",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}


