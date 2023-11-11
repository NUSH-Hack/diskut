package com.example.diskut.View.Onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.unit.dp
import com.example.diskut.Model.User
import com.example.diskut.Model.UserType
import com.example.diskut.PreferencesManager
import com.example.diskut.View.MainPage.MainPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsPage() {
    val user by remember {
        mutableStateOf(
            User(
                "h2000000@nushigh.edu.sg",
                "John",
                UserType.STUDENT,
                "Year 1",
                0
            )
        )
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedUserType by remember { mutableStateOf(UserType.STUDENT) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = user.name,
            onValueChange = { user.name = it },
            label = { Text("Name") }
        )
        TextField(
            value = user.email,
            onValueChange = { user.email = it },
            label = { Text("Email") }
        )
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                value = selectedUserType.toString(),
                onValueChange = {},
                label = { Text("Type") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                UserType.values().forEach { userType ->
                    DropdownMenuItem(
                        text = { Text(userType.toString()) },
                        onClick = { selectedUserType = userType; expanded = false }
                    )
                }
            }
        }
        TextField(
            value = user.value,
            onValueChange = { user.value = it },
            label = {
                Text(
                    when (selectedUserType) {
                        UserType.STUDENT -> "Year"
                        UserType.TEACHER -> "Department"
                        UserType.STAFF -> "Position"
                    }
                )
            }
        )
    }
}