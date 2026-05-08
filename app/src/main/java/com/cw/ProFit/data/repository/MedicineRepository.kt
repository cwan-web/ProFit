package com.cw.ProFit.data.repository

import com.cw.ProFit.data.models.MedicationModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class MedicineRepository (private
                          val supabase: SupabaseClient
) {
    suspend fun getMedications(): List<MedicationModel> {
        return supabase.from("medications")
            .select()
            .decodeList<MedicationModel>()
    }

    suspend fun addMedication(medication: MedicationModel) {
        // Explicitly inserting as a single item list often avoids issues with some versions of Postgrest-kt
        supabase.from("medications").insert(listOf(medication))
    }



}


