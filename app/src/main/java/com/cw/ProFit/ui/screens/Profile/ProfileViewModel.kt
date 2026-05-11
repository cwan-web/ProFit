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

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    init {
        fetchProfile()
    }

    fun clearMessage() {
        _message.value = null
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
                _message.value = "Failed to load profile"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(fullName: String, bloodType: String, allergies: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = SupabaseProvider.client.auth.currentUserOrNull()
                if (user == null) {
                    _message.value = "User session not found. Please log in again."
                    return@launch
                }
                val userId = user.id
                
                println("PROFIT_LOG: Starting update coroutine for $userId")
                val currentProfile = repository.getProfile(userId) ?: _profile.value
                val updatedProfile = ProfileModel(
                    id = userId,
                    fullName = fullName,
                    bloodType = bloodType,
                    allergies = allergies,
                    avatarUrl = currentProfile?.avatarUrl
                )
                repository.updateProfile(updatedProfile)
                _profile.value = updatedProfile
                _message.value = "Profile updated successfully"
            } catch (e: Exception) {
                println("PROFIT_LOG: Profile update failed in VM: ${e.message}")
                e.printStackTrace()
                _message.value = "Update failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfileWithImage(fullName: String, bloodType: String, allergies: String, imageBytes: ByteArray) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = SupabaseProvider.client.auth.currentUserOrNull()
                if (user == null) {
                    _message.value = "User session not found. Please log in again."
                    return@launch
                }
                val userId = user.id

                println("PROFIT_LOG: Starting image upload for $userId")
                val fileName = "profile_$userId.jpg"
                val imageUrl = repository.uploadProfileImage(fileName, imageBytes)
                
                val currentProfile = repository.getProfile(userId) ?: _profile.value
                val finalImageUrl = imageUrl ?: currentProfile?.avatarUrl
                
                val updatedProfile = ProfileModel(
                    id = userId,
                    fullName = fullName,
                    bloodType = bloodType,
                    allergies = allergies,
                    avatarUrl = finalImageUrl
                )

                repository.updateProfile(updatedProfile)
                
                val uiProfile = updatedProfile.copy(
                    avatarUrl = finalImageUrl?.let { if (it.contains("?")) "$it&t=${System.currentTimeMillis()}" else "$it?t=${System.currentTimeMillis()}" }
                )
                _profile.value = uiProfile
                _message.value = "Profile and photo updated successfully"
            } catch (e: Exception) {
                println("PROFIT_LOG: Profile/Photo update failed in VM: ${e.message}")
                e.printStackTrace()
                _message.value = "Update failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfileImage(byteArray: ByteArray) {
        val userId = SupabaseProvider.client.auth.currentUserOrNull()?.id ?: return
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val fileName = "profile_$userId.jpg"
                val imageUrl = repository.uploadProfileImage(fileName, byteArray)
                
                if (imageUrl != null) {
                    val currentProfile = repository.getProfile(userId) ?: _profile.value
                    val updatedProfile = (currentProfile ?: ProfileModel(id = userId)).copy(
                        avatarUrl = imageUrl
                    )
                    repository.updateProfile(updatedProfile)
                    
                    val uiProfile = updatedProfile.copy(
                        avatarUrl = "$imageUrl?t=${System.currentTimeMillis()}"
                    )
                    _profile.value = uiProfile
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
