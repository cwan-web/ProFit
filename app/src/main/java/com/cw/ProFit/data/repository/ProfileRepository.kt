package com.cw.ProFit.data.repository

import com.cw.ProFit.data.models.ProfileModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ProfileRepository(private val supabase: SupabaseClient) {
    
    suspend fun getProfile(userId: String): ProfileModel? {
        return try {
            supabase.from("profiles")
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
        supabase.from("profiles").upsert(profile)
    }
}
