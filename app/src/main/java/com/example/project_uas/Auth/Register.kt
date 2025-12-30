package com.example.project_uas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.databinding.ActivityRegisterBinding
import com.example.project_uas.Model.Profile
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
            val profile = Profile(
                id = UUID.randomUUID().toString(),
                nama = binding.etNama.text.toString(),
                email = binding.etEmail.text.toString(),
                phone = binding.etHp.text.toString(),
                username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString()
            )

            lifecycleScope.launch {
                try {
                    SupabaseClient.client
                        .postgrest["profiles"]
                        .insert(profile)

                    Toast.makeText(
                        this@Register,
                        "Registrasi berhasil",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(
                        this@Register,
                        e.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
