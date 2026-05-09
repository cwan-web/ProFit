package com.cw.ProFit.ui.screens.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cw.ProFit.data.models.UserModel
import com.cw.ProFit.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun registerUser(userModel: UserModel, fullName: String, onRegisterSuccess: () -> Unit) {
        _isLoading.value = true
        _message.value = ""
        viewModelScope.launch {
            try {
                authRepository.registerUser(userModel, fullName)
                _isLoading.value = false
                _message.value = "Registration successful!"
                onRegisterSuccess()
            } catch (e: Exception) {
                _isLoading.value = false
                _message.value = "Registration failed: ${e.message}"
            }
        }
    }
}
