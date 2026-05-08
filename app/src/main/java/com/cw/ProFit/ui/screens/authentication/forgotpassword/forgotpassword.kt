package com.cw.ProFit.ui.screens.authentication.forgotpassword

import android.R.attr.maxLines
import android.R.attr.shape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.cw.ProFit.R
import com.cw.ProFit.ui.theme.primaryColor
import com.cw.ProFit.ui.theme.secondaryColor


@Composable
fun ForgotPasswordScreen(onBack: () -> Unit, modifier: Modifier = Modifier, viewModel: ForgotPasswordViewModel = viewModel()) {
    //text Input

    var emailInput by remember { mutableStateOf(TextFieldValue("")) }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        //lottie aniamtion
        LottieAnimationWidget(R.raw.authlogin, 300.dp)


        // sIGNuP MESSAGE
        Text(
            text = "Forgot password?",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Enter your email to receive a password reset link",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        //emailinput
        OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Email",
                    tint = primaryColor
                )
            },
            placeholder = {
                Text(text = "eg. user@gmail.com")
            },
            label = { Text("Email Address") },
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        if (uiState is ForgotPasswordUiState.Error) {
            Text(
                text = (uiState as ForgotPasswordUiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        if (uiState is ForgotPasswordUiState.Success) {
            Text(
                text = "Instructions sent! Check your email.",
                color = Color.Green,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        //button
        OutlinedButton(
            onClick = { viewModel.sendResetEmail(emailInput.text) },
            enabled = uiState !is ForgotPasswordUiState.Loading,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            if (uiState is ForgotPasswordUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = primaryColor)
            } else {
                Text(
                    "Send Reset Link",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Back to Login",
            modifier = Modifier.clickable { onBack() },
            color = primaryColor,
            fontWeight = FontWeight.Medium
        )
    }
}



@Composable
fun LottieAnimationWidget(x0: Int, x1: Dp) {
    val composition by
    rememberLottieComposition(LottieCompositionSpec.RawRes(x0))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(x1)
    )
}

