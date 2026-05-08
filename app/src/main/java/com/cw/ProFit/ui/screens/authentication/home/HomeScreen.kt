package com.cw.ProFit.ui.screens.authentication.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cw.ProFit.data.AuthViewModel
import com.cw.ProFit.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val myauth = remember { AuthViewModel(navController, context) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ProFit") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = primaryColor,
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "settings icon",
                            tint = primaryColor
                        )
                    }
                    IconButton(onClick = { myauth.logout() }) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "logout icon",
                            tint = primaryColor
                        )
                    }
                }
            )
        },

        bottomBar = {
            BottomAppBar(
                containerColor = primaryColor,
                contentColor = Color.White
            ) {
                NavigationBar(
                    containerColor = primaryColor,
                ) {
                    NavigationBarItem(
                        selected = true,
                        onClick = { navController.navigate("home") },
                        icon = {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Home icon"
                            )
                        },
                        label = { Text("Home") }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate("profile") },
                        icon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Person icon"
                            )
                        },
                        label = { Text("Profile") }
                    )

                    NavigationBarItem(
                        selected = false,
                        onClick = { navController.navigate("settings") },
                        icon = {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = "Settings icon"
                            )
                        },
                        label = { Text("Settings") }
                    )
                }
            }
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Welcome to ProFit",
                modifier = Modifier.padding(16.dp),
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = primaryColor)
            )

            var username by remember { mutableStateOf("Loading...") }
            LaunchedEffect(Unit) {
                myauth.getCurrentUserName {
                    username = it
                }
            }

            Text(
                text = "Welcome, $username ",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Gray
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card 1: Add Medication
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            navController.navigate("add_medication")
                        },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = primaryColor,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Add Medication", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "icon",
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
                    }
                }

                // Card 2: Medication List
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            navController.navigate("home")
                        },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = primaryColor,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Medication List", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "icon",
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
                    }
                }

                // Card 3: My Profile
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            navController.navigate("profile")
                        },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = primaryColor,
                        contentColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("My Profile", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "icon",
                            modifier = Modifier.size(32.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun HomePreview() {
    HomeScreen(rememberNavController())
}
