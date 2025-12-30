package com.example.project_uas.supabase

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {

    private const val SUPABASE_URL = "https://cvkhkojpseezixecsbul.supabase.co"
    private const val SUPABASE_KEY = "sb_publishable_UeOaHie6QOuXCQ1Lt6Q_Mw_MJPpOvlK"

    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
    ) {
        install(Postgrest)
    }
}