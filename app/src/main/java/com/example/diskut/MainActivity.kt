package com.example.diskut

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.navigation.compose.rememberNavController
import com.example.diskut.Controller.fetchLeaderboard
import com.example.diskut.Controller.uploadLeaderboard
import com.example.diskut.Model.User
import com.example.diskut.Model.UserType
import com.example.diskut.View.QuestPage.Quest
import com.example.diskut.ui.theme.AppTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

val test_users: List<User> = listOf(
    User("", "Prannaya", UserType.STUDENT,"Year 6", 1000),
    User("", "Warren", UserType.STUDENT, "Year 4", 600),
    User("", "Mr. Meow", UserType.TEACHER, "Chemistry Department", 2)
)

val test_quest: List<Quest> = listOf<Quest>(
    Quest(
        description = "Talk to 100 people",
        completed = 1,
        goal = 100
    ),
    Quest(
        description = "Talk to 100 people",
        completed = 1,
        goal = 100
    ),
    Quest(
        description = "Talk to 100 people",
        completed = 1,
        goal = 100
    ),
    Quest(
        description = "Talk to 100 people",
        completed = 1,
        goal = 100
    )
)

class MainActivity : ComponentActivity() {
    private lateinit var bluetoothAdapter: BluetoothAdapter

    private lateinit var bluetooth: Bluetooth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val remoteConfig = com.google.firebase.Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
        CoroutineScope(Dispatchers.IO).launch() {
            uploadLeaderboard(fetchLeaderboard())
        }

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
                    App(leaderboard = test_users, bluetooth = bluetooth)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothTest(
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
                val receiver = ByteArray(1024)
                bluetooth.receive(receiver)
                meow = receiver
            }
        ) {
            Text("receive")
        }
        Text(meow.decodeToString().trim())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun App(leaderboard: List<User>, bluetooth: Bluetooth) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MyNavigationBar(navController)
        }
    ) { padding_values ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding_values)){
            NavigationHost(navController = navController, bluetooth = bluetooth)
        }
    }
}


//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun AppPreview() {
//    AppTheme {
//        App(leaderboard = test_users)
//    }
//}