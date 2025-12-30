package com.example.project_uas.Home

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
import kotlin.text.toDouble

class RumusBangunDatarBangunRuang : AppCompatActivity() {
    private lateinit var binding : ActivityRumusBangunDatarBangunRuangBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRumusBangunDatarBangunRuangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // untuk judul dan deskripsi
        val j = intent.getStringExtra("judul")
        val d = intent.getStringExtra("deskripsi")

        binding.judul.text = j
        binding.deskripsi.text = d

        // untuk luas
        val inputPanjang: EditText = findViewById(R.id.inputPanjang)
        val inputLebar: EditText = findViewById(R.id.inputLebar)
        val button2: Button = findViewById(R.id.button2)
        val hasil: TextView = findViewById(R.id.hasil)

        // untuk volume
        val inputPanjangBalok : EditText = findViewById(R.id.inputPanjangBalok)
        val inputLebarBalok : EditText = findViewById(R.id.inputLebarBalok)
        val inputTinggiBalok : EditText = findViewById(R.id.inputTinggiBalok)
        val buttonBalok : Button = findViewById(R.id.buttonBalok)
        val hasilBalok : TextView = findViewById(R.id.hasilBalok)

        // untuk luas
        button2.setOnClickListener {
            val pString = inputPanjang.text.toString()
            val lString = inputLebar.text.toString()
            val p = pString.toDouble()
            val l = lString.toDouble()
            val luas = p * l
            hasil.text = "$luas"

        }

        // untuk volume
        buttonBalok.setOnClickListener {
            val pString = inputPanjangBalok.text.toString()
            val lString = inputLebarBalok.text.toString()
            val tString = inputTinggiBalok.text.toString()

            val p = pString.toDouble()
            val l = lString.toDouble()
            val t = tString.toDouble()
            val volume = p * l * t
            hasilBalok.text = "$volume"
        }
    }
}