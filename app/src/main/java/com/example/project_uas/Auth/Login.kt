package com.example.project_uas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.Home.BaseActivity
import com.example.project_uas.databinding.ActivityLoginBinding
import com.example.project_uas.Profile.Profile
import com.example.project_uas.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tombolLogin.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi username dan password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val user = SupabaseClient.client
                        .postgrest["profiles"]
                        .select {
                            filter {
                                eq("username", username)
                                eq("password", password)
                            }
                        }
                        .decodeSingle<Profile>()

                    // SIMPAN SESSION KE PENYIMPANAN INTERNAL HP
                    val session = com.example.project_uas.utils.SessionManager(this@Login)
                    session.saveLoginSession(user.nama, user.email, user.phone)

                    Toast.makeText(this@Login, "Login berhasil, ${user.nama}", Toast.LENGTH_SHORT)
                        .show()

                    startActivity(Intent(this@Login, BaseActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@Login, "Username atau password salah", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }
}