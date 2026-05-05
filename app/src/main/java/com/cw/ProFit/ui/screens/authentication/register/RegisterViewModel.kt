package com.cw.ProFit.ui.screens.authentication.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cw.ProFit.data.models.UserModel
import com.cw.ProFit.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class RegisterUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class RegisterViewModel : ViewModel() {

    val authRepository = AuthRepository()

    //     state
    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _message = MutableStateFlow("")
    val message = _message.asStateFlow()


    //     methods
    fun registerUser(userModel: UserModel) {
        _isLoading.value = true
        viewModelScope.launch {

            try {
                authRepository.registerUser(userModel)
                _isLoading.value =false
                _message.value="success!"
            }catch (e:Error){
                _isLoading.value =false
                _message.value="Oops! Something went wrong:${e.message}"
            }

        }
    }
}