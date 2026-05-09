package com.cw.ProFit.ui.screens.Profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cw.ProFit.data.AuthViewModel
import com.cw.ProFit.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, profileViewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel(navController, context) }
    
    val profile by profileViewModel.profile.collectAsState()

    val fullname = remember { mutableStateOf("Loading...") }
    val email = remember { mutableStateOf("Loading...") }

    // Fetch user data from Auth metadata
    LaunchedEffect(Unit) {
        authViewModel.getCurrentUserName { name ->
            fullname.value = name
        }
        authViewModel.getCurrentUserEmail { userEmail ->
            email.value = userEmail
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // Profile Icon
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier.size(100.dp),
                tint = primaryColor
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Card for details
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    ProfileDetailItem(label = "Full Name", value = profile?.fullName ?: fullname.value)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                    
                    ProfileDetailItem(label = "Email", value = email.value)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    ProfileDetailItem(label = "Blood Type", value = profile?.bloodType ?: "Not specified")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    ProfileDetailItem(label = "Allergies", value = profile?.allergies ?: "None")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Logout Button
            Button(
                onClick = { authViewModel.logout() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor
                )
            ) {
                Text("Logout", color = Color.White)
            }
        }
    }
}

@Composable
fun ProfileDetailItem(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = primaryColor
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileScreen(rememberNavController())
}
