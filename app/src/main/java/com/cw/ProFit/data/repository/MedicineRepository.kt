package com.cw.ProFit.data.repository

import com.cw.ProFit.data.models.MedicationModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage

class MedicineRepository(private val supabase: SupabaseClient) {
    
    suspend fun getMedications(): List<MedicationModel> {
        return try {
            // Get the current logged in user
            val userId = supabase.auth.currentUserOrNull()?.id
            
            if (userId == null) return emptyList()

            // Fetch only medications belonging to this user
            supabase.postgrest.from("medications").select {
                filter {
                    eq("user_id", userId)
                }
            }.decodeList<MedicationModel>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addMedication(medication: MedicationModel) {
        try {
            // Ensure we are inserting the medication with the current user's ID
            val userId = supabase.auth.currentUserOrNull()?.id
            val medicationToSave = if (medication.userId == null) {
                medication.copy(userId = userId)
            } else {
                medication
            }
            
            supabase.postgrest.from("medications").insert(medicationToSave)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e // Rethrow to show error in UI
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
