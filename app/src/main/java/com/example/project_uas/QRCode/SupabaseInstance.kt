import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.engine.cio.CIO

object SupabaseInstance {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://cvkhkojpseezixecsbul.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImN2a2hrb2pwc2Vleml4ZWNzYnVsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM4ODUyMjEsImV4cCI6MjA3OTQ2MTIyMX0.ljVwoDVl0VvNJS2rSB1h4tau5Va0dBh7M_y5-5bOyeI"
    ) {
        httpEngine = CIO.create()
        install(Postgrest)
    }
}
