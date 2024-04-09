/**
 * Author Korovkina Valentina
 * Created at 09/04/24
 *
 * */
package com.example.session2.common

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object DbCon {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://fmidaoaqqrmcmgzquwjg.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZtaWRhb2FxcXJtY21nenF1d2pnIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODkwNTk0NTAsImV4cCI6MjAwNDYzNTQ1MH0.xWAVF40YfTRb4ZvrAwsiPIZ-gGdgI4v_rSV6X_iG_7c"
    ){
        install(Auth)
        install(Postgrest)
    }
}