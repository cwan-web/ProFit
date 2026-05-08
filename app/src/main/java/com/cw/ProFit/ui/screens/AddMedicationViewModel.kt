package com.cw.ProFit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cw.ProFit.data.SupabaseProvider
import com.cw.ProFit.data.models.MedicationModel
import com.cw.ProFit.data.repository.MedicineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.withTimeout

class AddMedicationViewModel : ViewModel() {
    private val repository = MedicineRepository(SupabaseProvider.client)

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun addMedication(name: String, category: String, dosage: String, onComplete: () -> Unit) {
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
                withTimeout(15000) { // 15 second timeout
                    val medication = MedicationModel(
                        name = trimmedName,
                        category = category.trim(),
                        defaultDosage = trimmedDosage
                    )
                    repository.addMedication(medication)
                }
                _isSaving.value = false
                onComplete()
            } catch (e: Exception) {
                _isSaving.value = false
                _errorMessage.value = "Error: ${e.localizedMessage ?: "Connection failed"}"
                e.printStackTrace()
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}
