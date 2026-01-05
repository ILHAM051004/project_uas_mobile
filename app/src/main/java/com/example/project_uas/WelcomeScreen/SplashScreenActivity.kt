package com.example.project_uas.WelcomeScreen

import android.content.Intent
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // Import Spesifik
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.Home.BaseActivity
import com.example.project_uas.databinding.ActivitySplashScreenBinding
import com.example.project_uas.utils.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // PERBAIKAN: Panggil installSplashScreen() SEBELUM super.onCreate tanpa parameter
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- ANIMASI ELEGAN ---
        binding.logo.alpha = 0f
        binding.logo.scaleX = 0.8f
        binding.logo.scaleY = 0.8f

        binding.logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(1200)
            .setInterpolator(DecelerateInterpolator())
            .start()

        val sessionManager = SessionManager(this)

        lifecycleScope.launch {
            delay(2500)

            if (sessionManager.isLoggedIn()) {
                val intent = Intent(this@SplashScreenActivity, BaseActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            } else {
                val intent = Intent(this@SplashScreenActivity, Welcome::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            finish()
        }
    }
}