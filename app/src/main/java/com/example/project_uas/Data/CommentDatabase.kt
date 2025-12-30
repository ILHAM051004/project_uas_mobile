package com.example.project_uas.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_uas.Data.Dao.CommentDao
import com.example.project_uas.Data.Entity.CommentEntity

// Perbaikan: Tambahkan exportSchema = false
@Database(entities = [CommentEntity::class], version = 1, exportSchema = false)
abstract class CommentDatabase : RoomDatabase() {

    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: CommentDatabase? = null

        fun getDatabase(context: Context): CommentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CommentDatabase::class.java,
                    "comment_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}