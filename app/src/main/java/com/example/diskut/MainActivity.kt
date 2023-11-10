package com.example.diskut

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
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
import com.example.diskut.ui.theme.AppTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.diskut.ui.theme.AppTheme
import java.io.IOException

import java.util.UUID

class MainActivity : ComponentActivity() {
    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private lateinit var socket: BluetoothServerSocket
    private val uuid = UUID.fromString("317a9c6e-90d1-418b-afa4-b5866a52bb5c")

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
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
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
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN,
                ),
                1337
            )
        }

        setContent {
            AppTheme {
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
                        acceptThread = AcceptThread(),
                        warren = {
                            val warrenPhone = bluetoothAdapter.getRemoteDevice("30:74:67:52:97:D8")

                        }
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

    inner class AcceptThread : Thread() {

        private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
            bluetoothAdapter?.listenUsingRfcommWithServiceRecord("duskit", uuid)
        }

        override fun run() {
            // Keep listening until exception occurs or a socket is returned.
            var shouldLoop = true
            while (shouldLoop) {
                val socket: BluetoothSocket? = try {
                    mmServerSocket?.accept()
                } catch (e: IOException) {
                    Log.e("duskit", "Socket's accept() method failed", e)
                    shouldLoop = false
                    null
                }
                socket?.also {
                    handleSocket(socket)
                    Log.i("duskit", "socket: $it")
                    mmServerSocket?.close()
                    shouldLoop = false
                }
            }
        }

        // Closes the connect socket and causes the thread to finish.
        fun cancel() {
            try {
                mmServerSocket?.close()
            } catch (e: IOException) {
                Log.e("duskit", "Could not close the connect socket", e)
            }
        }
    }

    fun handleSocket(socket: BluetoothSocket) {

    }

}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    discoverer: () -> Unit,
    discoveree: () -> Unit,
    endDiscover: () -> Unit,
    acceptThread: MainActivity.AcceptThread,
    warren: () -> Unit
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = { acceptThread.start() }
            ) {
                Text("open socket")
            }
            Button(
                onClick = { acceptThread.cancel() }
            ) {
                Text("close socket")
            }
        }
        Button(
            onClick = warren
        ) {
            Text("connect to warren")
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