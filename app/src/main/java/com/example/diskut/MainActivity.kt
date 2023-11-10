package com.example.diskut

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.diskut.ui.theme.AppTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import java.io.IOException
import java.util.UUID

class MainActivity : ComponentActivity() {
    private lateinit var bluetoothAdapter: BluetoothAdapter

    private lateinit var bluetooth: Bluetooth

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

        // Requests perms
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

        bluetoothAdapter = lazy {
            val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothManager.adapter
        }.value
        bluetooth = Bluetooth(bluetoothAdapter)

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(
                        server = {
                            bluetooth.startServer { Log.d("diskut", "server started") }
                        },
                        client = {
                            bluetooth.startClient { Log.d("diskut", "client started") }
                        },
                        bluetooth = bluetooth
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    server: () -> Unit,
    client: () -> Unit,
    bluetooth: Bluetooth,
) {
    val context = LocalContext.current

    var text by remember { mutableStateOf("Hello") }
    var meow by remember { mutableStateOf(ByteArray(1024)) }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = ":3",
            modifier = modifier
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    Toast.makeText(context, "Server", Toast.LENGTH_SHORT).show()
                    server()
                }
            ) {
                Text("server")
            }
            Button(
                onClick = {
                    Toast.makeText(context, "Client", Toast.LENGTH_SHORT).show()
                    client()
                }
            ) {
                Text("client")
            }
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") }
        )
        Button(
            onClick = {
                Log.i("diskut", "send clicked: $text")
                bluetooth.send(text.toByteArray())
            }
        ) {
            Text("send")
        }
        Button(
            onClick = {
                Log.i("diskut", "receive clicked")
                bluetooth.receive(meow)
                Log.i("diskut", meow.decodeToString())
                Toast.makeText(context, meow.decodeToString().substringBefore('\u0000'), Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("receive")
        }
    }
}