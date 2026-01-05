package com.example.project_uas.WelcomeScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.project_uas.Home.BaseActivity
import com.example.project_uas.Login
import com.example.project_uas.R
import com.example.project_uas.databinding.ActivityWelcomeBinding

class Welcome : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup ViewPager dengan adapter
        val fragmentsList = listOf(PertamaFragment(), KeduaFragment(), KetigaFragment())
        val adapter = WelcomeFragmentAdapter(this, fragmentsList)
        binding.welcomeViewPage.adapter = adapter

        binding.dotIndicator.attachTo(binding.welcomeViewPage)

        // untuk skip
        binding.btnSkip.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        binding.welcomeViewPage.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.btnSkip.text = if (position == fragmentsList.lastIndex) "Mulai" else "Skip"
            }
        })
    }
}
