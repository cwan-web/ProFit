package com.cw.ProFit.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseProvider {
    val client = createSupabaseClient(
        supabaseUrl = "https://zjejqqbhmdtsmsdsjvjy.supabase.co",
        supabaseKey = "sb_publishable_lhOscPBscvFghcb-0xbO7w_8mt6ZNpW"
    ) {
        install(Postgrest)
        install(Auth)
    }
}
