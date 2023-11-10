package com.example.diskut.View.QuestPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.diskut.test_quest
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfig
import org.json.JSONObject

@Composable
internal fun QuestList(questList: List<Quest>) {
//    val remoteConfig = Firebase.remoteConfig
//    val questJson = remoteConfig["global_quest"].asString()
//    val json = JSONObject(questJson)

    val questList = remember { mutableStateListOf<Quest>() }

    //
    //    questList.add(Quest(
    //        json.getString("Description"),
    //        json.getInt("Completed"),
    //        json.getInt("Goal"),
    //    ))

    questList.addAll(test_quest)

    LazyColumn(modifier = Modifier
        .fillMaxSize(),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(questList, itemContent = { item ->
            QuestCard(item)
        })
    }
}

