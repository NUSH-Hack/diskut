package com.example.diskut.Controller

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.delay

@Composable
fun PointIncrement(expanded: Boolean, currPoints: MutableState<Int>) {
    Log.i("diskut", "Composable drawn")
    LaunchedEffect(expanded) {
        while (true) {
            delay(1000)
            Log.i("diskut", "Launched effect")

            if (expanded) {
                currPoints.value += 1
                Log.i("diskut", "${currPoints.value}")

            }
        }
    }
}