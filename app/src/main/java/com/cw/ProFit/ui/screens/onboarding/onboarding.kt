package com.cw.ProFit.ui.screens.onboarding

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.cw.ProFit.ui.navigation.ROUTES


@Composable
fun OnboardingScreen(navController: NavHostController){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedButton(onClick = { navController.navigate(ROUTES.Login.name)}) {
            Text(text ="Proceed")
        }
    }
}