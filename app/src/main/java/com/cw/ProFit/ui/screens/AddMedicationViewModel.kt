package com.cw.ProFit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cw.ProFit.data.SupabaseProvider
import com.cw.ProFit.data.models.MedicationModel
import com.cw.ProFit.data.repository.MedicineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.withTimeout

class AddMedicationViewModel : ViewModel() {
    private val repository = MedicineRepository(SupabaseProvider.client)

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun addMedication(
        name: String,
        category: String,
        dosage: String,
        instructions: String,
        imageBytes: ByteArray? = null,
        onComplete: () -> Unit
    ) {
        val trimmedName = name.trim()
        val trimmedDosage = dosage.trim()
        
        if (trimmedName.isBlank() || trimmedDosage.isBlank()) {
            _errorMessage.value = "Name and dosage are required"
            return
        }

        viewModelScope.launch {
            _isSaving.value = true
            _errorMessage.value = null
            try {
                val currentUser = SupabaseProvider.client.auth.currentUserOrNull()
                val userId = currentUser?.id

                if (userId == null) {
                    _errorMessage.value = "You must be logged in to add medication"
                    _isSaving.value = false
                    return@launch
                }

                var imageUrl: String? = null
                if (imageBytes != null) {
                    val fileName = "med_${userId}_${System.currentTimeMillis()}.jpg"
                    imageUrl = repository.uploadMedicationImage(fileName, imageBytes)
                }

                val medication = MedicationModel(
                    name = trimmedName,
                    category = category.trim(),
                    defaultDosage = trimmedDosage,
                    instructions = instructions.trim(),
                    userId = userId,
                    imageUrl = imageUrl
                )
                
                repository.addMedication(medication)
                
                _isSaving.value = false
                onComplete()
            } catch (e: Exception) {
                _isSaving.value = false
                _errorMessage.value = "Failed to save: ${e.localizedMessage ?: "Unknown error"}"
                e.printStackTrace()
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}
