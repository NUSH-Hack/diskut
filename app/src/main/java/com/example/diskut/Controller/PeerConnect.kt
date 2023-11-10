package com.example.diskut.Controller

import android.util.Log
import com.example.diskut.Bluetooth
import com.example.diskut.Model.User

fun PeerConnect(currUser: User, bluetooth: Bluetooth, onConnect: (User) -> Unit): User {
    val peer = ByteArray(1024)

    if (bluetooth.adapter.name == "Jing's A12") {
        Log.i("diskut", "warren")
        bluetooth.startServer {
            Log.i("diskut", "connection established")
            bluetooth.send(currUser.serialize())
            bluetooth.receive(peer)
        }
    } else {
        Log.i("diskut", "not warren")
        bluetooth.startClient {
            Log.i("diskut", "connection established")
            bluetooth.send(currUser.serialize())
            bluetooth.receive(peer)
        }
    }

    val peerUser = User.deserialize(peer)

    onConnect(peerUser)

    return peerUser
}