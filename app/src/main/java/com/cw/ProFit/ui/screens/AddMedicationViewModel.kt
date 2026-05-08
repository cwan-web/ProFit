package com.cw.ProFit.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cw.ProFit.data.SupabaseProvider
import com.cw.ProFit.data.models.MedicationModel
import com.cw.ProFit.data.repository.MedicineRepository
import kotlinx.coroutines.launch

class AddMedicationViewModel : ViewModel() {
    private val repository = MedicineRepository(SupabaseProvider.client)

    fun addMedication(name: String, category: String, dosage: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                val medication = MedicationModel(
                    name = name,
                    category = category,
                    defaultDosage = dosage
                )
                repository.addMedication(medication)
                onComplete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
