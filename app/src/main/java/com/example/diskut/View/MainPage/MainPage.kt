package com.example.diskut.View.MainPage

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.diskut.Bluetooth
import com.example.diskut.Controller.GenerateConversationCues
import com.example.diskut.MainActivity
import com.example.diskut.Model.User
import com.example.diskut.Model.UserType
import com.example.diskut.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val yueheng = User("h2010157@nushigh.edu.sg", "Wong Yue Heng", UserType.STUDENT, "Year 4", 0)
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
    var currPoints by remember { mutableStateOf(270) }
    val conversationCues = remember { mutableStateListOf("Before the list is filled") }

    var peerUser by remember { mutableStateOf(User("", "", UserType.STAFF, "", 0)) }

    val interactionSource = remember { MutableInteractionSource() }

    val currUser = if (bluetooth.adapter.name == "Jing's A12") {
        warren
    } else {
        yueheng
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(interactionSource = interactionSource, indication = null) {
                val peer = ByteArray(1024)

                if (bluetooth.adapter.name == "Jing's A12") {
                    Log.i("diskut", "warren")
                    bluetooth.startServer {
                        Log.i("diskut", "connection established")
                        bluetooth.send(currUser.serialize())
                        bluetooth.receive(peer)

                        peerUser = User.deserialize(peer)

                        expanded = !expanded

                        conversationCues.clear()
                        CoroutineScope(Dispatchers.Main).launch {
                            conversationCues.addAll(GenerateConversationCues(peerUser, currUser))
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

                        conversationCues.clear()
                        CoroutineScope(Dispatchers.Main).launch {
                            conversationCues.addAll(GenerateConversationCues(peerUser, currUser))
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
                currPoints = currUser.points,
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
                expanded = expanded
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