package com.cw.ProFit.data.repository

import com.cw.ProFit.data.models.ProfileModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage

class ProfileRepository(private val supabase: SupabaseClient) {
    
    suspend fun getProfile(userId: String): ProfileModel? {
        return try {
            supabase.postgrest.from("profiles")
                .select {
                    filter {
                        eq("id", userId)
                    }
                }
                .decodeSingleOrNull<ProfileModel>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateProfile(profile: ProfileModel) {
        try {
            println("PROFIT_LOG: Attempting to upsert profile for user: ${profile.id}")
            supabase.postgrest.from("profiles").upsert(profile)
            println("PROFIT_LOG: Upsert successful")
        } catch (e: Exception) {
            println("PROFIT_LOG: Upsert failed with error: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    suspend fun uploadProfileImage(fileName: String, byteArray: ByteArray): String? {
        return try {
            val bucket = supabase.storage.from("profile-images")
            println("Uploading profile image: $fileName")
            bucket.upload(fileName, byteArray) {
                upsert = true
            }
            val url = bucket.publicUrl(fileName)
            println("Upload successful. Public URL: $url")
            url
        } catch (e: Exception) {
            println("Upload failed: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}
