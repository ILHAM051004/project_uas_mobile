package com.example.project_uas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project_uas.Comment.CommentFragment
import com.example.project_uas.Home.HomeFragment
import com.example.project_uas.Wahana.WahanaFragment
import com.example.project_uas.Tiket.TiketFragment
import com.example.project_uas.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Tampilan default saat pertama kali dibuka
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.wahana -> {
                    replaceFragment(WahanaFragment())
                    true
                }
                R.id.tiket -> {
                    replaceFragment(TiketFragment())
                    true
                }
                R.id.comment -> {
                    replaceFragment(CommentFragment())
                    true
                }
                R.id.profile -> {
                    // PERBAIKAN: Gunakan replaceFragment, bukan Intent
                    replaceFragment(ProfileFragment())
                    true // Set true agar menu terpilih secara visual
                }
                else -> false
            }
        }
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}