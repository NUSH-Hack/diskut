package com.example.diskut.Controller

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun PointIncrement(expanded: Boolean, currPoints: MutableState<Int>) {
    Log.i("diskut", "Composable drawn")
    LaunchedEffect(expanded) {
        var num = 0
        while (true) {
            delay(1000)
//            Log.i("diskut", "Launched effect")

            if (expanded) {
                currPoints.value += if (num < 35) {
                    1
                } else if (Random.nextFloat() < 5.0 / (num - 30.0)) {
                    1
                } else {
                    0
                }
//                Log.i("diskut", "${currPoints.value}")
            }
            num++
        }
    }
}