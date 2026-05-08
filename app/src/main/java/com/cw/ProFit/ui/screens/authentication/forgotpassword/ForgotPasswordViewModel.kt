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

    private var currentEmail: String = ""

    fun sendResetEmail(email: String) {
        if (email.isBlank()) {
            _uiState.value = ForgotPasswordUiState.Error("Please enter your email")
            return
        }
        currentEmail = email

        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            try {
                repository.resetPassword(email)
                _uiState.value = ForgotPasswordUiState.CodeSent
            } catch (e: Exception) {
                _uiState.value = ForgotPasswordUiState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun verifyCode(code: String, onNavigateToHome: () -> Unit) {
        if (code.isBlank()) {
            _uiState.value = ForgotPasswordUiState.Error("Please enter the code")
            return
        }

        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            try {
                repository.verifyOtp(currentEmail, code)
                _uiState.value = ForgotPasswordUiState.Success
                onNavigateToHome()
            } catch (e: Exception) {
                _uiState.value = ForgotPasswordUiState.Error(e.message ?: "Invalid code")
            }
        }
    }
}

sealed class ForgotPasswordUiState {
    object Idle : ForgotPasswordUiState()
    object Loading : ForgotPasswordUiState()
    object CodeSent : ForgotPasswordUiState()
    object Success : ForgotPasswordUiState()
    data class Error(val message: String) : ForgotPasswordUiState()
}
