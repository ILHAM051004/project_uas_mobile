package com.example.project_uas.Comment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.Data.CommentDatabase
import com.example.project_uas.Data.Entity.CommentEntity
import com.example.project_uas.R
import com.example.project_uas.databinding.ActivityCommentFormBinding
import kotlinx.coroutines.launch

class CommentFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentFormBinding
    private lateinit var db: CommentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // untuk keterangan toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Komentar"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // inisialisasi database
        db = CommentDatabase.getDatabase(this)

        binding.btnSimpan.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val komentar = binding.etKomentar.text.toString().trim()

            if (nama.isNotEmpty() && komentar.isNotEmpty()) {
                lifecycleScope.launch {
                    val comment = CommentEntity(
                        nama = nama,
                        isiKomentar = komentar,
                        tanggal = System.currentTimeMillis()
                    )
                    db.commentDao().insertComment(comment)
                    finish()
                }
            } else {
                Toast.makeText(this, "Isi semua kolom!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}