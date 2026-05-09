package com.cw.ProFit.data.repository

import com.cw.ProFit.data.models.MedicationModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage

class MedicineRepository(private val supabase: SupabaseClient) {
    
    suspend fun getMedications(): List<MedicationModel> {
        // Try getting the user ID
        val userId = supabase.auth.currentUserOrNull()?.id
        if (userId == null) {
            println("MedicineRepository: No user logged in")
            return emptyList()
        }

        return try {
            println("MedicineRepository: Fetching medications for user $userId")
            val result = supabase.postgrest.from("medications")
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<MedicationModel>()
            println("MedicineRepository: Successfully fetched ${result.size} medications")
            result
        } catch (e: Exception) {
            println("MedicineRepository: Error fetching medications: ${e.localizedMessage}")
            e.printStackTrace()
            throw e // Let the ViewModel handle the error
        }
    }

    suspend fun addMedication(medication: MedicationModel) {
        try {
            val userId = supabase.auth.currentUserOrNull()?.id
            val medicationToSave = if (medication.userId == null) {
                medication.copy(userId = userId)
            } else {
                medication
            }
            
            println("MedicineRepository: Adding medication: ${medicationToSave.name}")
            supabase.postgrest.from("medications").insert(medicationToSave)
            println("MedicineRepository: Successfully added medication")
        } catch (e: Exception) {
            println("MedicineRepository: Error adding medication: ${e.localizedMessage}")
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
