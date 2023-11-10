package com.example.diskut.View.QuestPage

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.diskut.ui.theme.AppTheme

@Composable
fun QuestPage(content_padding: Dp) {
    val questList = remember { mutableStateListOf<Quest>() }
    questList.addAll(q)

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(content_padding), state = rememberLazyListState()) {
        items(questList, itemContent = { item ->
            QuestCard(item)
        })
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
            QuestPage(32.dp)
        }
    }
}



internal val q: List<Quest> = listOf<Quest>(

)
