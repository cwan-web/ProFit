package com.cw.ProFit.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseProvider {
    val client = createSupabaseClient(
        supabaseUrl = "https://zjejqqbhmdtsmsdsjvjy.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InpqZWpxcWJobWR0c21zZHNqdmp5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzc4NjAyOTcsImV4cCI6MjA5MzQzNjI5N30.kBGgcJY0MEgB9ulGgJMDfaPH_eZz36DhNHRQ5gkXsR4"
    ) {
        install(Postgrest)
        install(Auth)
        install(io.github.jan.supabase.realtime.Realtime)
        install(io.github.jan.supabase.storage.Storage)
    }
}
