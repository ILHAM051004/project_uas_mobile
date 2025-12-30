package com.example.project_uas.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.project_uas.Data.Entity.CommentEntity

@Dao
interface CommentDao {

    @Query("SELECT * FROM comments")
    suspend fun getAllComments(): List<CommentEntity>

    @Insert
    suspend fun insertComment(comment: CommentEntity)

    @Delete
    suspend fun deleteComment(comment: CommentEntity)
}
