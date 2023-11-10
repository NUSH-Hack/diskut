package com.example.diskut.Controller

import com.example.diskut.Model.User

fun GenerateConversationCues(peerUser: User, currUser: User): List<String> {
    // do some logic here
    return listOf("You are both stupid", "You are both silly", "You are both CS majors")
}