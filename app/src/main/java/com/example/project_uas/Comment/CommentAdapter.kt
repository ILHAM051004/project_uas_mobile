package com.example.project_uas.Comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_uas.Data.Entity.CommentEntity
import com.example.project_uas.databinding.ItemCommentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class CommentAdapter(
    private val comments: List<CommentEntity>,
    private val commentFragment: CommentFragment
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]

        holder.binding.tvNama.text = comment.nama
        holder.binding.tvKomentar.text = comment.isiKomentar

        // Format tanggal
        val formattedDate = android.text.format.DateFormat.format(
            "dd MMM yyyy - HH:mm",
            comment.tanggal
        ).toString()

        holder.binding.tvTanggal.text = formattedDate

        holder.itemView.setOnClickListener {
            Snackbar.make(
                holder.itemView,
                "Dipilih: ${comment.nama}",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        holder.binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Hapus Komentar")
                .setMessage("Apakah Anda yakin ingin menghapus komentar ini?")
                .setPositiveButton("Ya") { dialog, _ ->
                    commentFragment.deleteComment(comment)
                    dialog.dismiss()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }


    override fun getItemCount(): Int = comments.size
}
