package com.example.project_uas.Content

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_uas.R
import com.example.project_uas.databinding.ActivityRumusBangunDatarBangunRuangBinding

class RumusBangunDatarBangunRuang : AppCompatActivity() {
    private lateinit var binding : ActivityRumusBangunDatarBangunRuangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRumusBangunDatarBangunRuangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Konfigurasi Toolbar dengan Tombol Back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Kalkulator Rumus"
            setDisplayHomeAsUpEnabled(true) // Menampilkan tombol back
            setDisplayShowHomeEnabled(true)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Ambil data intent
        val j = intent.getStringExtra("judul")
        val d = intent.getStringExtra("deskripsi")

        // Logika perhitungan tetap sama...
        // (Gunakan binding.etId untuk akses view agar lebih rapi)

        binding.button2.setOnClickListener {
            val p = binding.inputPanjang.text.toString().toDoubleOrNull() ?: 0.0
            val l = binding.inputLebar.text.toString().toDoubleOrNull() ?: 0.0
            binding.hasil.text = "Hasil: ${p * l}"
        }

        binding.buttonBalok.setOnClickListener {
            val p = binding.inputPanjangBalok.text.toString().toDoubleOrNull() ?: 0.0
            val l = binding.inputLebarBalok.text.toString().toDoubleOrNull() ?: 0.0
            val t = binding.inputTinggiBalok.text.toString().toDoubleOrNull() ?: 0.0
            binding.hasilBalok.text = "Hasil: ${p * l * t}"
        }
    }

    // Fungsi agar tombol back di toolbar berfungsi
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}