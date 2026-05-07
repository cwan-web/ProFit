package com.cw.ProFit.ui.screens.authentication.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.websocket.Frame

@Composable
fun HomeScreen(modifier: Modifier, onNavigateToAddMedication: () -> Unit){


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()

    ) {
        Frame.Text(text = "Test your FITNESS")


    }

}


