package com.example.project_uas.Home

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.Data.Api.HarimauApiClient
import com.example.project_uas.R
import com.example.project_uas.databinding.ActivityTiketBinding
import kotlinx.coroutines.launch

class Tiket : AppCompatActivity() {
    private lateinit var binding: ActivityTiketBinding
    private val apiKey = "Q2lBbaKeQZKezz6YCVyNQg==fXYdwWxpTYGFYfgP"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTiketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // untuk keterangan toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Tiket"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //untuk judul dan deskripsi
        val jt = intent.getStringExtra("judul")
        val dt = intent.getStringExtra("deskripsi")

        binding.judul.text = jt
        binding.deskripsi.text = dt

    }


    // untuk tombol back berfungsi toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}