package com.cw.ProFit.ui.screens.Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cw.ProFit.data.SupabaseProvider
import com.cw.ProFit.data.models.ProfileModel
import com.cw.ProFit.data.repository.ProfileRepository
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = ProfileRepository(SupabaseProvider.client)

    private val _profile = MutableStateFlow<ProfileModel?>(null)
    val profile: StateFlow<ProfileModel?> = _profile.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchProfile()
    }

    fun fetchProfile() {
        val userId = SupabaseProvider.client.auth.currentUserOrNull()?.id ?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val profileData = repository.getProfile(userId)
                _profile.value = profileData
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
