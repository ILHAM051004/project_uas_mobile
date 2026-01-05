package com.example.project_uas.Content

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project_uas.R
import com.example.project_uas.databinding.ActivityHewanBinding

class Hewan : AppCompatActivity() {
    private lateinit var binding: ActivityHewanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHewanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // untuk keterangan toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Hewan"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Menampilkan fragment pertama secara default
        replaceFragment(HewanHarimau())

        // Setup event click untuk mengganti fragment
        binding.harimau.setOnClickListener {
            replaceFragment(HewanHarimau())
        }

        binding.serigala.setOnClickListener {
            replaceFragment(HewanSerigala())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
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