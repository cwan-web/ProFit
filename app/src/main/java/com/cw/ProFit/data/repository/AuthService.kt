package com.cw.ProFit.data.repository

import com.cw.ProFit.data.models.UserModel

interface AuthService {
    suspend fun registerUser(user: UserModel, fullName: String)
    suspend fun loginUser(user: UserModel)
    suspend fun resetPassword(email: String)
    suspend fun verifyOtp(email: String, token: String)
    suspend fun getUserProfile(user: UserModel)
    suspend fun logoutUser()
}
