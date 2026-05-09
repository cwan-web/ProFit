package com.cw.ProFit.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cw.ProFit.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Account Settings",
                style = MaterialTheme.typography.titleLarge,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SettingsItem(
                icon = Icons.Default.Person,
                title = "Profile Information",
                subtitle = "Name, Email, etc."
            )
            SettingsItem(
                icon = Icons.Default.Lock,
                title = "Privacy & Security",
                subtitle = "Passwords, Data access"
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Notifications",
                style = MaterialTheme.typography.titleLarge,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val pushNotifications = remember { mutableStateOf(true) }
            val emailNotifications = remember { mutableStateOf(false) }

            SettingsToggleItem(
                icon = Icons.Default.Notifications,
                title = "Push Notifications",
                state = pushNotifications.value,
                onToggle = { pushNotifications.value = it }
            )
            SettingsToggleItem(
                icon = Icons.Default.Notifications,
                title = "Email Notifications",
                state = emailNotifications.value,
                onToggle = { emailNotifications.value = it }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "About",
                style = MaterialTheme.typography.titleLarge,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            SettingsItem(
                icon = Icons.Default.Info,
                title = "App Version",
                subtitle = "1.0.0"
            )
            SettingsItem(
                icon = Icons.Default.Info,
                title = "Terms of Service",
                subtitle = "Read our legal information"
            )
        }
    }
}

@Composable
fun SettingsItem(icon: ImageVector, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = primaryColor.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontSize = 18.sp, color = Color.Black)
            Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun SettingsToggleItem(icon: ImageVector, title: String, state: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = primaryColor.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 18.sp, color = Color.Black, modifier = Modifier.weight(1.0f))
        Switch(
            checked = state, 
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = primaryColor,
                uncheckedThumbColor = Color.LightGray,
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.3f)
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SettingsScreen(rememberNavController())
}
