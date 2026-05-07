package com.cw.ProFit.data.repository


import com.cw.ProFit.data.models.UserModel
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class AuthRepository: AuthService {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://zjejqqbhmdtsmsdsjvjy.supabase.co,",
        supabaseKey = "sb_publishable_lhOscPBscvFghcb-0xbO7w_8mt6ZNpW"
    )  {
        install(Postgrest)
        install(Auth)
    }



    override suspend fun registerUser(user: UserModel) {
       supabase.auth.signUpWith(Email){
           email = user.email
           this.password
       }
    }

    override suspend fun loginUser(userDetails: UserModel)  {

    }

    override suspend fun resetPassword(email: String) {
        supabase.auth.resetPasswordForEmail(email = email)
    }

    override suspend fun getUserProfile(user: UserModel) {
//        TODO("Not yet implemented")
    }

    override suspend fun logoutUser() {
        supabase.auth.signOut()
    }




}