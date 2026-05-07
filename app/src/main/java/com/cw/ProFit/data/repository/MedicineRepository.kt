package com.cw.ProFit.data.repository

import com.cw.ProFit.data.models.MedicationModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class MedicineRepository (private
    val supabase: SupabaseClient
) {
    suspend fun getMedications(): List<MedicationModel> {
        return emptyList()
        supabase.from("medications")
            .select()
            .decodeList<MedicationModel>()


    }



}




