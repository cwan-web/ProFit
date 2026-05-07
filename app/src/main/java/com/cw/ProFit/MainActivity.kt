package com.cw.ProFit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.cw.ProFit.ui.navigation.AppNavigation
import com.cw.ProFit.ui.screens.authentication.forgotpassword.ForgotPaswordScreen
import com.cw.ProFit.ui.screens.authentication.home.HomeScreen
import com.cw.ProFit.ui.screens.authentication.login.LoginScreen
import com.cw.ProFit.ui.theme.ProFitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProFitTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    AppNavigation(navController, modifier = Modifier.padding( paddingValues = innerPadding))

                }
            }
        }
    }
}

