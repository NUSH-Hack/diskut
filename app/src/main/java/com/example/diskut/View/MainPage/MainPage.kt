package com.example.diskut.View.MainPage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.unit.dp
import com.example.diskut.Bluetooth
import com.example.diskut.Controller.GenerateConversationCues
import com.example.diskut.Controller.PeerConnect
import com.example.diskut.Model.User
import com.example.diskut.Model.UserType

val yueheng = User("h2010157@nushigh.edu.sg", "Wong Yue Heng", UserType.STUDENT, "Year 4", 0)
val warren = User("h2230006@nushigh.edu.sg", "Warren Zhou", UserType.STUDENT, "Year 4", 10)


@Composable
fun MainPage(goalPoints: Int, bluetooth: Bluetooth) {
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
                peerUser = PeerConnect(currUser, bluetooth) {
                    conversationCues.clear()
                    conversationCues.addAll(GenerateConversationCues(it, currUser))
                }
                expanded = !expanded
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