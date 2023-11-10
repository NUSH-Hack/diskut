package com.example.diskut

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.diskut.ui.theme.DiskutTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private lateinit var socket: BluetoothServerSocket
    private val uuid = UUID.randomUUID()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action!!
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE
                        )!!
                    val deviceName = device.name
                    val deviceMAC = device.address
                    Log.i("diskut", "Found device: $deviceName ($deviceMAC)")

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("diskut", "requesting perms")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
                1337
            )
        }

        setContent {
            DiskutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(
                        ":3",
                        discoverer = this::startDiscover,
                        discoveree = this::makeDiscoverable,
                        endDiscover = this::endDiscover,
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(receiver)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i("diskut", "meowow $requestCode")
        when (requestCode) {
            1337 -> {
                Log.i("diskut", "perms request accepted")
                registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
            }

            else -> {}
        }
    }

    fun startDiscover() {
        if (bluetoothAdapter.isDiscovering) endDiscover()
        bluetoothAdapter.startDiscovery()
    }

    fun endDiscover() {
        bluetoothAdapter.cancelDiscovery()
    }

    fun makeDiscoverable() {
        val requestCode = 1;
        val discoverableIntent: Intent =
            Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            }
        startActivityForResult(discoverableIntent, requestCode)
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    discoverer: () -> Unit,
    discoveree: () -> Unit,
    endDiscover: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row {
                Button(
                    onClick = discoverer
                ) {
                    Text("start discovery")
                }
            }
            Row {
                Button(
                    onClick = endDiscover
                ) {
                    Text("end discovery")
                }
            }
        }
        Button(
            onClick = discoveree
        ) {
            Text("be discovered")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    DiskutTheme {
//        Greeting("Android")
//    }
//}