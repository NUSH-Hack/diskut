package com.example.diskut.View.MainPage

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.diskut.Bluetooth
import com.example.diskut.Controller.GenerateConversationCues
import com.example.diskut.Controller.PointIncrement
import com.example.diskut.Model.User
import com.example.diskut.Model.UserType
import com.example.diskut.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


val yueheng = User("h2010157@nushigh.edu.sg", "Wong Yue Heng", UserType.STUDENT, "Year 4", 50)
val warren = User("h2230006@nushigh.edu.sg", "Warren Zhou", UserType.STUDENT, "Year 4", 10)


@Composable
fun MainPage(goalPoints: Int, bluetooth: Bluetooth) {
    yueheng.addMajor("Math")
    yueheng.addMajor("Chemistry")
    yueheng.addMajor("Computer Science")
    warren.addMajor("Math")
    warren.addMajor("Physics")
    warren.addMajor("Computer Science")
    yueheng.addTeacher("Lim Chong Shen")
    yueheng.addTeacher("Claude Chua")
    warren.addTeacher("Tan Guan Seng")
    warren.addTeacher("Claude Chua")
    yueheng.addInterest("Gardening")
    warren.addInterest("Landscaping")
    var expanded by remember { mutableStateOf(false) }
    val conversationCues = remember { mutableStateListOf("Before the list is filled") }

    var peerUser by remember { mutableStateOf(User("", "", UserType.STAFF, "", 0)) }

    val currUser = if (bluetooth.adapter.name == "Jing's A12") {
        warren
    } else {
        yueheng
    }

    val currPoints = remember { mutableStateOf(currUser.points) }

    val filter = IntentFilter()
    filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)

    val bluetoothTurnedOnOff = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            Log.i("diskut", "state changed !!")
            if (intent?.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1) == BluetoothAdapter.STATE_OFF) {
                expanded = false
                bluetooth.stop()
            }
        }
    }

    LocalContext.current.registerReceiver(bluetoothTurnedOnOff, filter)
    val mPlayerHappy = MediaPlayer.create(LocalContext.current, R.raw.happy)
    PointIncrement(expanded = expanded, currPoints = currPoints)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable() {
                val peer = ByteArray(1024)

                if (bluetooth.adapter.name == "Jing's A12") {
                    Log.i("diskut", "warren")
                    bluetooth.startServer {
                        Log.i("diskut", "connection established")
                        bluetooth.send(currUser.serialize())
                        bluetooth.receive(peer)

                        peerUser = User.deserialize(peer)

                        expanded = !expanded
                        mPlayerHappy.start()

                        conversationCues.clear()
                        CoroutineScope(Dispatchers.Main).launch {
                            conversationCues.addAll(GenerateConversationCues(peerUser, currUser))
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            while (true) {
                                Log.i("diskut",
                                    bluetooth
                                        .active()
                                        .toString()
                                )
                                if (!bluetooth.active()) {
                                    expanded = false
                                }
                                delay(1000)
                            }
                        }
                    }
                } else {
                    Log.i("diskut", "not warren")
                    bluetooth.startClient {
                        Log.i("diskut", "connection established")
                        bluetooth.send(currUser.serialize())
                        bluetooth.receive(peer)

                        peerUser = User.deserialize(peer)

                        expanded = !expanded
                        mPlayerHappy.start()

                        conversationCues.clear()
                        CoroutineScope(Dispatchers.Main).launch {
                            conversationCues.addAll(GenerateConversationCues(peerUser, currUser))
                        }
                        CoroutineScope(Dispatchers.IO).launch {
                            while (true) {
                                Log.i("diskut",
                                    bluetooth
                                        .active()
                                        .toString()
                                )
                                if (!bluetooth.active()) {
                                    expanded = false
                                }
                                delay(1000)
                            }
                        }
                    }
                }
            }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            AnimatedPointsIndicator(
                modifier = Modifier.weight(0.1f),
                username = currUser.name,
                currPoints = currPoints.value,
                goalPoints = goalPoints,
                expanded = expanded
            )

            AnimatedConversationCues(
                modifier = Modifier.weight(10.0f),
                conversationCues = conversationCues,
                expanded = expanded
            )

            Spacer(modifier = Modifier.weight(0.1f))

            AnimatedPeerDetails(
                modifier = Modifier.weight(0.1f),
                username = peerUser.name,
                occupation = "${peerUser.type} - ${peerUser.value}",
                expanded = expanded,
                onClick = {
//                    expanded = false
                    bluetooth.stop()
                }
            )

        }
    }
}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun MainPagePreview() {
//    AppTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            MainPage(1000)
//        }
//    }
//}