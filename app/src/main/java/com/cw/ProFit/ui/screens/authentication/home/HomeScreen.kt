package com.cw.ProFit.ui.screens.authentication.home

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentDataType.Companion.Text
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import io.ktor.http.ContentType
import io.ktor.websocket.Frame

@Composable
fun HomeScreen(modifier: Modifier){


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()

    ) {
        Frame.Text(text = "Test your FITNESS")


    }

}


