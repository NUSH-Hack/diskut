package com.example.diskut.Controller

import com.example.diskut.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import org.json.JSONArray

fun FetchLeaderboard(): ArrayList<User> {
    val storage = Firebase.storage
    var storageRef = storage.reference

    lateinit var leaderboardJson: JSONArray
    storageRef.child("leaderboard.json").getBytes(1024 * 1024).addOnSuccessListener {
        leaderboardJson = JSONArray(it.toString())
    }.addOnFailureListener {
        leaderboardJson = JSONArray()
            .put(User("", "Prannaya", 6, 1000).serialize())
            .put(User("", "Warren", 4, 600).serialize())
            .put(User("", "Meow", 1, 2).serialize())
    }

    val leaderboard: ArrayList<User> = arrayListOf()
    for (i in 0..2) {
        leaderboard.add(User.deserialize(leaderboardJson.getString(i).toByteArray()))
    }

    //    leaderboard.add() TODO: add the current user here
    leaderboard.sortBy { it.points }
    leaderboard.reverse() // leaderboard is now 1st, then 2nd, then 3rd

    return leaderboard
}

fun UploadLeaderboard(){
    // Upload leaderboard
    val leaderboardJson = JSONArray()
    for (i in 0..2) {
        leaderboardJson.put(leaderboard[i].serialize())
    }
    storageRef.child("leaderboard.json").putBytes(leaderboardJson.toString().toByteArray())

}