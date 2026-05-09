package com.cw.ProFit.ui.screens.authentication.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cw.ProFit.data.SupabaseProvider
import com.cw.ProFit.data.models.MedicationModel
import com.cw.ProFit.data.repository.MedicineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val repository = MedicineRepository(SupabaseProvider.client)

    // 1. Private mutable state
    private val _medications = MutableStateFlow<List<MedicationModel>>(emptyList())

    // 2. Public read-only state
    val medications: StateFlow<List<MedicationModel>> = _medications.asStateFlow()

    init {
        fetchMedications()
    }

    fun fetchMedications() {
        viewModelScope.launch {
            try {
                // This calls the Supabase function we fixed earlier
                val list = repository.getMedications()
                _medications.value = list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
