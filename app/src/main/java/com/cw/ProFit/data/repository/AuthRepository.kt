package com.cw.ProFit.data.repository

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.OtpType
import com.cw.ProFit.data.models.UserModel
import com.cw.ProFit.data.SupabaseProvider

class AuthRepository: AuthService {
    val supabase = SupabaseProvider.client

    override suspend fun registerUser(user: UserModel) {
        supabase.auth.signUpWith(Email) {
            email = user.email
            password = user.password
        }
    }

    override suspend fun loginUser(userDetails: UserModel) {
        supabase.auth.signInWith(Email) {
            email = userDetails.email
            password = userDetails.password
        }
    }

    override suspend fun resetPassword(email: String) {
        // Sends an OTP code to the email
        supabase.auth.signInWith(OTP) {
            this.email = email
        }
    }

    override suspend fun verifyOtp(email: String, token: String) {
        // Using a safe way to get the enum value if possible
        supabase.auth.verifyEmailOtp(
            type = OtpType.Email.valueOf("EMAIL"),
            email = email,
            token = token
        )
    }

    override suspend fun getUserProfile(user: UserModel) {
        // Implementation for profile
    }

    override suspend fun logoutUser() {
        supabase.auth.signOut()
    }
}
