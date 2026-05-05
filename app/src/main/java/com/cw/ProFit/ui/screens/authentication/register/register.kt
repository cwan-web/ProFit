package com.cw.ProFit.ui.screens.authentication.register

import com.cw.ProFit.R
import com.cw.ProFit.ui.screens.authentication.forgotpassword.LottieAnimationWidget
import com.cw.ProFit.ui.theme.primaryColor
import com.cw.ProFit.ui.theme.secondaryColor
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
import android.R.attr.text
import android.provider.ContactsContract
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










@Composable
fun RegisterScreen(modifier: Modifier) {
    //text Input
    var emailInput by remember { mutableStateOf(TextFieldValue("")) }
    var passwordInput by remember { mutableStateOf(TextFieldValue("")) }
    var isVisible by remember { mutableStateOf(false) }
    var userName  by remember { mutableStateOf(TextFieldValue(""))}

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()

    ) {

        //lottie aniamtion
        LottieAnimationWidget(R.raw.mandoingdumbellcurls, 300.dp)


        // sIGNuP MESSAGE
        Text(
            text = "Sign UP",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        //username
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("userName")}

        )






        //emailinput
        OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            leadingIcon = {
                Icon(
                    imageVector = Outlined.Email,
                    contentDescription = "Email",
                    tint = primaryColor
                )
            },
            placeholder = {
                Text(text = "eg. gh@gmail.com")
            },
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
        //password input

        OutlinedTextField(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.outline_password_2_24),
                    contentDescription = "Password",
                    tint = primaryColor
                )
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = if (isVisible) {
                VisualTransformation.None

            } else {
                PasswordVisualTransformation()
            },


            trailingIcon = {
                IconButton(
                    onClick = { var isViisble = !isVisible }
                ) {
                    if (isVisible) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.outline_visibility_24),
                            contentDescription = "Password"
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.outline_visibility_off_24),
                            contentDescription = "Password"
                        )
                    }

                }
            },
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(24.dp))
        //button
        OutlinedButton(
            onClick = { }
        )
        {
            Text(
                "Sign in",
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        //row
        Row() {

            Text(text = "Login in")



        }
    }
}


