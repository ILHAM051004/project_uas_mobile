package com.example.project_uas.Home

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_uas.R
import com.example.project_uas.databinding.ActivityTiketBinding
import com.example.project_uas.databinding.ActivityWikiBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.tabs.TabLayoutMediator

class WikiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWikiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWikiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // untuk keterangan toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Wiki ONNX"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // 1. Inisialisasi Adapter
        val tabsAdapter = WikiTabsAdapter(this)

        // 2. Set adapter ke ViewPager2
        binding.viewPager.adapter = tabsAdapter

        // 3. Hubungkan TabLayout & ViewPager2 menggunakan Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // Atur judul untuk setiap tab
            when (position) {
                0 -> {
                    tab.text = "Wiki"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.wiki)
                    tab.icon?.setTint(ContextCompat.getColor(this, android.R.color.white))
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                }
                1 -> {
                    tab.text = "Model"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.model)
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 2
                }
                2 -> {
                    tab.text = "Konversi"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.konversi)
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                    badge.number = 1
                }
                3 -> {
                    tab.text = "Implementasi"
                    tab.icon = ContextCompat.getDrawable(this, R.drawable.implementasi)
                    val badge = tab.getOrCreateBadge()
                    badge.isVisible = true
                }
            }
        }.attach()

        // warna icon dan teks tab (tanpa file baru)
        val iconColorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_selected), // saat tab aktif
                intArrayOf(-android.R.attr.state_selected) // saat tab tidak aktif
            ),
            intArrayOf(
                ContextCompat.getColor(this, R.color.white), // aktif
                ContextCompat.getColor(this, android.R.color.black)  // tidak aktif
            )
        )

        with(binding.tabLayout) {
            tabIconTint = iconColorStateList
            setTabTextColors(
                ContextCompat.getColor(this@WikiActivity, android.R.color.black),
                ContextCompat.getColor(this@WikiActivity, R.color.white)
            )
            elevation = 8f // sedikit bayangan biar lebih elegan
        }
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