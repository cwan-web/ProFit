package com.cw.ProFit.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.cw.ProFit.data.repository.AuthRepository
import io.github.jan.supabase.auth.auth
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
        val user = SupabaseProvider.client.auth.currentUserOrNull()
        // Try getting name from metadata
        val name = user?.userMetadata?.get("full_name")?.toString()?.removeSurrounding("\"") ?: "User"
        callback(name)
    }

    fun getCurrentUserEmail(callback: (String) -> Unit) {
        val user = SupabaseProvider.client.auth.currentUserOrNull()
        callback(user?.email ?: "No email")
    }
}
