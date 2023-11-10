package com.example.diskut.Controller

import android.util.Log
import com.example.diskut.Model.User

suspend fun GenerateConversationCues(peerUser: User, currUser: User): List<String> {
    // do some logic here
    val cues : ArrayList<String> = arrayListOf()
    currUser.findCommonMajor(peerUser).forEach {
        cues.add("You both take $it")
    }
    currUser.findCommonTeacher(peerUser).forEach {
        cues.add("You've both been taught by $it")
    }
    val commonInterests = currUser.findCommonInterests(peerUser)
    for (i in 0..2) {
        if (commonInterests.isNotEmpty()) {
            val commonInterest = commonInterests.removeFirst()
            cues.add("You like ${commonInterest.interestOne}, they like ${commonInterest.interestTwo}")
        }
    }
    return cues.toList()
}