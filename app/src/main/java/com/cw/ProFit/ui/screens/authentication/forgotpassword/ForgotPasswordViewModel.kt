package com.cw.ProFit.ui.screens.authentication.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cw.ProFit.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState

    fun sendResetEmail(email: String) {
        if (email.isBlank()) {
            _uiState.value = ForgotPasswordUiState.Error("Please enter your email")
            return
        }

        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            try {
                repository.resetPassword(email)
                _uiState.value = ForgotPasswordUiState.Success
            } catch (e: Exception) {
                _uiState.value = ForgotPasswordUiState.Error(e.message ?: "An error occurred")
            }
        }
    }
}

sealed class ForgotPasswordUiState {
    object Idle : ForgotPasswordUiState()
    object Loading : ForgotPasswordUiState()
    object Success : ForgotPasswordUiState()
    data class Error(val message: String) : ForgotPasswordUiState()
}
