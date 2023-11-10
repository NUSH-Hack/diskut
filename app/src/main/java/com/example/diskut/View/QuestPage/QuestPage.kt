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
import androidx.compose.foundation.lazy.items
import com.example.diskut.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.storage.ktx.storage
import org.json.JSONArray
import org.json.JSONObject

@Composable
fun QuestPage(content_padding: Dp) {
    // Leaderboard code

    val storage = Firebase.storage
    var storageRef = storage.reference
    lateinit var leaderboardJson: JSONArray
    storageRef.child("leaderboard.json").getBytes(1024*1024).addOnSuccessListener {
        leaderboardJson = JSONArray(it.toString())
    }.addOnFailureListener {
        leaderboardJson = JSONArray()
            .put(User("", "Prannaya", 6, 1000).serialize())
            .put(User("", "Warren", 4, 600).serialize())
            .put(User("", "Meow", 1, 2).serialize())
    }
    val leaderboard : ArrayList<User> = arrayListOf()
    for (i in 0..2) {
        leaderboard.add(User.deserialize(leaderboardJson.getString(i).toByteArray()))
    }
//    leaderboard.add() TODO: add the current user here
    leaderboard.sortBy { it.points }
    leaderboard.reverse() // leaderboard is now 1st, then 2nd, then 3rd

    // Upload leaderboard
    leaderboardJson = JSONArray()
    for (i in 0..2) {
        leaderboardJson.put(leaderboard[i].serialize())
    }
    storageRef.child("leaderboard.json").putBytes(leaderboardJson.toString().toByteArray())

    // Quest code below
    val questList = remember { mutableStateListOf<Quest>() }
    val remoteConfig = Firebase.remoteConfig
    val questJson = remoteConfig["global_quest"].asString()
    val json = JSONObject(questJson)
    questList.add(Quest(
        json.getString("Description"),
        json.getInt("Completed"),
        json.getInt("Goal"),
    ))
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
