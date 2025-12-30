package com.example.project_uas

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.databinding.ActivityRegisterBinding
import com.example.project_uas.Profile.Profile
import com.example.project_uas.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.util.UUID

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val hp = binding.etHp.text.toString().trim()
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // 1. Cek Kolom Kosong
            if (nama.isEmpty() || email.isEmpty() || hp.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Cek Format Email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Format email tidak valid"
                return@setOnClickListener
            }

            // 3. Cek Nomor HP (Hanya Angka & Min 10 digit)
            if (hp.length < 10 || !hp.all { it.isDigit() }) {
                binding.etHp.error = "Nomor HP harus berupa angka (min 10 digit)"
                return@setOnClickListener
            }

            // 4. Cek Minimal Password
            if (password.length < 6) {
                binding.etPassword.error = "Password minimal 6 karakter"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val profile = Profile(UUID.randomUUID().toString(), nama, email, hp, username, password)
                    SupabaseClient.client.postgrest["profiles"].insert(profile)

                    Toast.makeText(this@Register, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@Register, "Gagal: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}