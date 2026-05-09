package com.cw.ProFit.data.repository

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import com.cw.ProFit.data.models.UserModel
import com.cw.ProFit.data.SupabaseProvider

class AuthRepository: AuthService {
    val supabase = SupabaseProvider.client

    override suspend fun registerUser(user: UserModel, fullName: String) {
        // 1. Create the user in Supabase Auth with metadata
        // Adding 'data' here makes the name reflect in the Supabase Auth Dashboard
        supabase.auth.signUpWith(Email) {
            email = user.email
            password = user.password
            data = buildJsonObject {
                put("full_name", fullName)
            }
        }

        // 2. Try to create/update the profile in the 'profiles' table
        // We use currentUserOrNull() to get the ID if the user was signed in automatically
        val userId = supabase.auth.currentUserOrNull()?.id
        
        if (userId != null) {
            try {
                val profile = com.cw.ProFit.data.models.ProfileModel(
                    id = userId,
                    fullName = fullName
                )
                supabase.postgrest.from("profiles").upsert(profile)
            } catch (e: Exception) {
                println("Failed to create profile: ${e.message}")
            }
        }
    }

    override suspend fun loginUser(user: UserModel) {
        supabase.auth.signInWith(Email) {
            email = user.email
            password = user.password
        }
    }

    override suspend fun resetPassword(email: String) {
        // This sends a 6-digit OTP code to the email for Passwordless Login
        // Ensure your Supabase Email template uses {{ .Token }} instead of {{ .ConfirmationURL }}
        supabase.auth.signInWith(OTP) {
            this.email = email
            // Set this to false if you only want existing users to be able to log in via OTP
            this.createUser = true
        }
    }

    override suspend fun verifyOtp(email: String, token: String) {
        // Verifies the 6-digit code and logs the user in
        supabase.auth.verifyEmailOtp(
            type = OtpType.Email.EMAIL,
            email = email,
            token = token
        )
    }

    override suspend fun getUserProfile(user: UserModel) {
        // Example: Fetching from the 'profiles' table using the user's ID
        val userId = supabase.auth.currentUserOrNull()?.id ?: return
        try {
            val profile = supabase.postgrest.from("profiles").select {
                filter {
                    eq("id", userId)
                }
            }.decodeSingleOrNull<com.cw.ProFit.data.models.ProfileModel>()
            println("Fetched profile: $profile")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun logoutUser() {
        supabase.auth.signOut()
    }
}
