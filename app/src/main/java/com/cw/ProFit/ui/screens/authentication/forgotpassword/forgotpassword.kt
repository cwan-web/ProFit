package com.cw.ProFit.ui.screens.authentication.forgotpassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.cw.ProFit.R
import com.cw.ProFit.ui.theme.primaryColor
import com.cw.ProFit.ui.theme.secondaryColor


@Composable
fun ForgotPasswordScreen(onBack: () -> Unit, onNavigateToHome: () -> Unit, modifier: Modifier = Modifier, viewModel: ForgotPasswordViewModel = viewModel()) {
    var emailInput by remember { mutableStateOf(TextFieldValue("")) }
    var codeInput by remember { mutableStateOf(TextFieldValue("")) }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        LottieAnimationWidget(R.raw.authlogin, 250.dp)

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
            text = if (uiState is ForgotPasswordUiState.CodeSent) 
                "Enter the 6-digit code sent to your email" 
            else 
                "Enter your email to receive a login code",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        if (uiState is ForgotPasswordUiState.CodeSent) {
            // OTP Code Input
            OutlinedTextField(
                value = codeInput,
                onValueChange = { codeInput = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Code",
                        tint = primaryColor
                    )
                },
                placeholder = { Text(text = "123456") },
                label = { Text("6-Digit Code") },
                maxLines = 1,
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = secondaryColor,
                    unfocusedBorderColor = primaryColor
                ),
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            // Email Input
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
                placeholder = { Text(text = "user@gmail.com") },
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
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        if (uiState is ForgotPasswordUiState.Error) {
            Text(
                text = (uiState as ForgotPasswordUiState.Error).message,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedButton(
            onClick = { 
                if (uiState is ForgotPasswordUiState.CodeSent) {
                    viewModel.verifyCode(codeInput.text, onNavigateToHome)
                } else {
                    viewModel.sendResetEmail(emailInput.text)
                }
            },
            enabled = uiState !is ForgotPasswordUiState.Loading,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            if (uiState is ForgotPasswordUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = primaryColor)
            } else {
                Text(
                    text = if (uiState is ForgotPasswordUiState.CodeSent) "Verify & Login" else "Send Code",
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
