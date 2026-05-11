package com.cw.ProFit.ui.screens.Profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cw.ProFit.data.AuthViewModel
import com.cw.ProFit.data.models.ProfileModel
import com.cw.ProFit.ui.theme.primaryColor

@Composable
fun ProfileScreen(navController: NavHostController, profileViewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel(navController, context) }
    
    val profile by profileViewModel.profile.collectAsState()
    val isLoading by profileViewModel.isLoading.collectAsState()
    val message by profileViewModel.message.collectAsState()

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

    ProfileContent(
        profile = profile,
        isLoading = isLoading,
        message = message,
        fullname = fullname.value,
        email = email.value,
        onUpdateProfile = { fn, bt, al -> profileViewModel.updateProfile(fn, bt, al) },
        onUpdateProfileWithImage = { fn, bt, al, bytes -> profileViewModel.updateProfileWithImage(fn, bt, al, bytes) },
        onUpdateProfileImage = { bytes -> profileViewModel.updateProfileImage(bytes) },
        onLogout = { authViewModel.logout() },
        onClearMessage = { profileViewModel.clearMessage() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    profile: ProfileModel?,
    isLoading: Boolean,
    message: String?,
    fullname: String,
    email: String,
    onUpdateProfile: (String, String, String) -> Unit,
    onUpdateProfileWithImage: (String, String, String, ByteArray) -> Unit,
    onUpdateProfileImage: (ByteArray) -> Unit,
    onLogout: () -> Unit,
    onClearMessage: () -> Unit
) {
    val context = LocalContext.current
    
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            if (bytes != null) {
                onUpdateProfileImage(bytes)
            }
        }
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var editFullName by remember { mutableStateOf("") }
    var editBloodType by remember { mutableStateOf("") }
    var editAllergies by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) }

    val editGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            val inputStream = context.contentResolver.openInputStream(it)
            selectedImageBytes = inputStream?.readBytes()
            inputStream?.close()
        }
    }

    LaunchedEffect(profile) {
        profile?.let {
            editFullName = it.fullName ?: ""
            editBloodType = it.bloodType ?: ""
            editAllergies = it.allergies ?: ""
        }
    }

    LaunchedEffect(message) {
        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            onClearMessage()
            if (it.contains("successfully") || it.contains("updated")) {
                showEditDialog = false
                selectedImageUri = null
                selectedImageBytes = null
            }
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

            // Profile Icon / Image
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { galleryLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = primaryColor)
                } else if (!profile?.avatarUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = profile?.avatarUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier.size(100.dp),
                        tint = primaryColor
                    )
                }
            }

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
                    ProfileDetailItem(label = "Full Name", value = profile?.fullName ?: fullname)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                    
                    ProfileDetailItem(label = "Email", value = email)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    ProfileDetailItem(label = "Blood Type", value = profile?.bloodType ?: "Not specified")
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    ProfileDetailItem(label = "Allergies", value = profile?.allergies ?: "None")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Edit Profile Button
            Button(
                onClick = { showEditDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor
                )
            ) {
                Text("Edit Profile", color = Color.White)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Logout Button
            Button(
                onClick = { onLogout() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {
                Text("Logout", color = Color.White)
            }
        }
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = {
                if (!isLoading) {
                    showEditDialog = false
                }
            },
            title = {
                Text(
                    text = "Edit Profile",
                    fontWeight = FontWeight.Bold
                )
            },

            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    // Profile Image
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                editGalleryLauncher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        when {
                            selectedImageUri != null -> {
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = "Selected Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            !profile?.avatarUrl.isNullOrEmpty() -> {
                                AsyncImage(
                                    model = profile?.avatarUrl,
                                    contentDescription = "Profile Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            else -> {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Default Avatar",
                                    modifier = Modifier.size(60.dp),
                                    tint = primaryColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = {
                            editGalleryLauncher.launch("image/*")
                        }
                    ) {
                        Text("Change Photo")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Full Name
                    OutlinedTextField(
                        value = editFullName,
                        onValueChange = { editFullName = it },
                        label = { Text("Full Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Blood Type
                    OutlinedTextField(
                        value = editBloodType,
                        onValueChange = { editBloodType = it },
                        label = { Text("Blood Type") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Allergies
                    OutlinedTextField(
                        value = editAllergies,
                        onValueChange = { editAllergies = it },
                        label = { Text("Allergies") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },

            confirmButton = {
                Button(
                    enabled = !isLoading,
                    onClick = {

                        if (editFullName.isBlank()) {
                            return@Button
                        }

                        val bytes = selectedImageBytes

                        if (bytes != null) {
                            onUpdateProfileWithImage(
                                editFullName.trim(),
                                editBloodType.trim(),
                                editAllergies.trim(),
                                bytes
                            )
                        } else {
                            onUpdateProfile(
                                editFullName.trim(),
                                editBloodType.trim(),
                                editAllergies.trim()
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    )
                ) {

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    } else {
                        Text("Save")
                    }
                }
            },

            dismissButton = {
                TextButton(
                    enabled = !isLoading,
                    onClick = {
                        showEditDialog = false
                        selectedImageUri = null
                        selectedImageBytes = null
                    }
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.Gray
                    )
                }
            }
        )
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
    ProfileContent(
        profile = ProfileModel(
            id = "1",
            fullName = "John Doe",
            bloodType = "O+",
            allergies = "None",
            avatarUrl = null
        ),
        isLoading = false,
        message = null,
        fullname = "John Doe",
        email = "john.doe@example.com",
        onUpdateProfile = { _, _, _ -> },
        onUpdateProfileWithImage = { _, _, _, _ -> },
        onUpdateProfileImage = { _ -> },
        onLogout = { },
        onClearMessage = { }
    )
}
