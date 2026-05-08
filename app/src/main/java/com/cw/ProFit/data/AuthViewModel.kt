package com.cw.ProFit.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.cw.ProFit.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val navController: NavHostController, private val context: Context) : ViewModel() {
    private val repository = AuthRepository()

    fun logout() {
        viewModelScope.launch {
            repository.logoutUser()
            navController.navigate("login") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    fun getCurrentUserName(callback: (String) -> Unit) {
        // Placeholder implementation
        callback("User")
    }
}
