package com.example.diskut.Controller

import android.util.Log
import com.example.diskut.Model.User
import com.example.diskut.Model.UserType
import com.example.diskut.View.MainPage.warren
import com.example.diskut.View.MainPage.yueheng
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import org.json.JSONArray

suspend fun fetchLeaderboard(): List<User> {
    Log.i("diskut", "fetchLeaderboard called")

    val storage = Firebase.storage
    var storageRef = storage.reference

    lateinit var leaderboardJson: JSONArray
    return storageRef.child("leaderboard.json").getBytes(1024 * 1024).addOnSuccessListener {
        Log.i("diskut", "fetch success!")
        leaderboardJson = JSONArray(it.decodeToString())
    }.addOnFailureListener {
        Log.i("diskut", "fetch fail!")
        leaderboardJson = JSONArray()
            .put(User("", "Prannaya", UserType.STUDENT,"Year 6", 1000).serialize().decodeToString())
            .put(User("", "Warren", UserType.STUDENT, "Year 4", 600).serialize().decodeToString())
            .put(User("", "Mr. Meow", UserType.TEACHER, "Chemistry Department", 2).serialize().decodeToString())
    }.continueWith {
        val leaderboard: MutableList<User> = mutableListOf()
        Log.i("diskut", User("", "Prannaya", UserType.STUDENT,"Year 6", 1000).serialize().decodeToString())
        Log.i("diskut", leaderboardJson.toString())
        for (i in 0..2) {
            leaderboard.add(User.deserialize(leaderboardJson.getString(i).toByteArray()))
        }
        leaderboard.add(warren)
        leaderboard.add(yueheng)
        leaderboard.sortBy { it.points }
        leaderboard.reverse() // leaderboard is now 1st, then 2nd, then 3rd
        return@continueWith leaderboard.toList()
    }.await()
}
fun uploadLeaderboard(leaderboard: List<User>) {
    // Upload leaderboard
    val leaderboardJson = JSONArray()
    for (i in 0..2) {
        leaderboardJson.put(leaderboard[i].serialize().decodeToString())
    }
    val storage = Firebase.storage
    var storageRef = storage.reference
    storageRef.child("leaderboard.json").putBytes(leaderboardJson.toString().toByteArray())

}