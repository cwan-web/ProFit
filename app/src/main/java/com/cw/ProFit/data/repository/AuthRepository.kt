package com.cw.ProFit.data.repository


import android.R.attr.password
import android.net.http.HttpResponseCache.install
import androidx.compose.ui.text.input.KeyboardType.Companion.Email
import com.cw.ProFit.data.models.UserModel
import com.google.android.gms.auth.api.Auth
import java.sql.DriverManager.println

class AuthRepository: AuthService {
    val supabase = createSupabaseClient(
            supabaseUrl = "https://gkaobjeuqrvzozuhujku.supabase.co",
            supabaseKey = "sb_publishable_AkttR3tZtoKy_huZb0ox4g_2KDpxwoJ"
         ) {
           install(Postgrest)
        }


    override suspend fun registerUser(userDetails: UserModel)  {
        supabase.auth.signUpWith(Email) {
            email = userDetails.email
            password = userDetails.password
        }
    }

    override suspend fun loginUser(userDetails: UserModel)  {
        val user = supabase.auth.signInWith(Email) {
            email = userDetails.email
            password = userDetails.password
        }
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