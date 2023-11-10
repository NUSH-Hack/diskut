package com.example.diskut.View.QuestPage

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diskut.ui.theme.AppTheme

import androidx.compose.foundation.lazy.items
import com.example.diskut.User
import com.example.diskut.UserType
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.storage.ktx.storage
import org.json.JSONArray
import org.json.JSONObject


@Composable
internal fun Leaderboard() {
    // Leaderboard code

    val storage = Firebase.storage
    var storageRef = storage.reference
    lateinit var leaderboardJson: JSONArray
    storageRef.child("leaderboard.json").getBytes(1024 * 1024).addOnSuccessListener {
        leaderboardJson = JSONArray(it.toString())
    }.addOnFailureListener {
        leaderboardJson = JSONArray()
            .put(User("", "Prannaya", UserType.STUDENT,"Year 6", 1000).serialize())
            .put(User("", "Warren", UserType.STUDENT, "Year 4", 600).serialize())
            .put(User("", "Mr. Meow", UserType.TEACHER, "Chemistry Department", 2).serialize())
    }
    val leaderboard: ArrayList<User> = arrayListOf()
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

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LeaderboardPreview() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            QuestPage(32.dp)
        }
    }
}
