package com.example.project_uas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.WelcomeScreen.Welcome
import com.example.project_uas.utils.SessionManager // Impor SessionManager
import com.example.project_uas.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi SessionManager
        val sessionManager = SessionManager(this)

        lifecycleScope.launch {
            delay(2000) // Delay 2 detik

            // LOGIKA PERUBAHAN: Cek status login
            if (sessionManager.isLoggedIn()) {
                // Jika sudah login, langsung ke halaman Utama (BaseActivity)
                val intent = Intent(this@SplashScreenActivity, BaseActivity::class.java)
                startActivity(intent)
            } else {
                // Jika belum login, arahkan ke Welcome Screen atau Login
                val intent = Intent(this@SplashScreenActivity, Welcome::class.java)
                startActivity(intent)
            }
            finish()
        }
    }
}