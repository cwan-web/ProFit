package com.cw.ProFit.data.repository

import com.cw.ProFit.data.models.MedicationModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage

class MedicineRepository(private val supabase: SupabaseClient) {
    
    suspend fun getMedications(): List<MedicationModel> {
        return try {
            // Fetch all medications from the table
            supabase.postgrest.from("medications")
                .select()
                .decodeList<MedicationModel>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addMedication(medication: MedicationModel) {
        try {
            val userId = supabase.auth.currentUserOrNull()?.id
            // Ensure userId is attached if it's missing
            val medicationToSave = if (medication.userId == null) {
                medication.copy(userId = userId)
            } else {
                medication
            }
            
            // Insert as a list to ensure compatibility with all Postgrest versions
            supabase.postgrest.from("medications").insert(listOf(medicationToSave))
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    suspend fun uploadMedicationImage(fileName: String, byteArray: ByteArray): String? {
        return try {
            val bucket = supabase.storage.from("medication-images")
            bucket.upload(fileName, byteArray) {
                upsert = true
            }
            bucket.publicUrl(fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteMedication(id: Long) {
        try {
            supabase.postgrest.from("medications").delete {
                filter {
                    eq("id", id)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
