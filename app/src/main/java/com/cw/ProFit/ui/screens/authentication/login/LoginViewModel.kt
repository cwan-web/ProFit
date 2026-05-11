package com.cw.ProFit.ui.screens.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cw.ProFit.data.models.UserModel
import com.cw.ProFit.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun loginUser(userModel: UserModel, onLoginSuccess: () -> Unit) {
        _isLoading.value = true
        _message.value = ""
        viewModelScope.launch {
            try {
                authRepository.loginUser(userModel)
                _isLoading.value = false
                _message.value = "Login successful!"
                onLoginSuccess()
            } catch (e: Exception) {
                _isLoading.value = false
                _message.value = "Login failed: ${e.message}"
            }
        }
    }
}
