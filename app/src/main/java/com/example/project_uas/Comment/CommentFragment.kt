package com.example.project_uas.Comment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_uas.Data.CommentDatabase
import com.example.project_uas.Data.Entity.CommentEntity
import com.example.project_uas.databinding.FragmentCommentBinding
import kotlinx.coroutines.launch

class CommentFragment : Fragment() {
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

    // variabel database & adapter
    private lateinit var db: CommentDatabase
    private lateinit var adapter: CommentAdapter
    private val comments = mutableListOf<CommentEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        /** Ganti menjadi versi binding */
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // untuk toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Tulis Komentar"
        }

        // inisialisasi database
        db = CommentDatabase.getDatabase(requireContext())
        adapter = CommentAdapter(comments, this)

        binding.rvComments.adapter = adapter
        binding.rvComments.layoutManager = LinearLayoutManager(requireContext())

        // garis pemisah
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvComments.addItemDecoration(dividerItemDecoration)

        fetchComments()

        binding.fabAddComment.setOnClickListener {
            startActivity(Intent(requireContext(), CommentFormActivity::class.java))
        }
    }

    private fun fetchComments(){
        lifecycleScope.launch {
            val data = db.commentDao().getAllComments()
            comments.clear()
            comments.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchComments()
    }

    fun deleteComment(comment: CommentEntity) {
        lifecycleScope.launch {
            db.commentDao().deleteComment(comment)
            fetchComments()
        }
    }
}