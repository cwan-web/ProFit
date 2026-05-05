package com.cw.ProFit.data.repository

import android.net.http.HttpResponseCache
import com.cw.ProFit.data.models.UserModel


interface AuthService {
    suspend fun registerUser(user: UserModel)
    suspend fun loginUser(user: UserModel)
    suspend fun resetPassword(email: String)
    suspend fun getUserProfile(user: UserModel)
    suspend fun logoutUser()
    fun createSupabaseClient(
        supabaseUrl: String,
        supabaseKey: String,
        function: () -> HttpResponseCache
    )

    fun install(directory: Any): HttpResponseCache
}