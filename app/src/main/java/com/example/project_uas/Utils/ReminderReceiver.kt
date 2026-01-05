package com.example.project_uas.Utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.project_uas.Home.BaseActivity
import com.example.project_uas.Utils.NotificationHelper

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Ambil data dari intent
        val title           = intent.getStringExtra("title") ?: "Pengingat Tiket"
        val message         = intent.getStringExtra("message") ?: "Cek tiket wahana Anda sekarang!"
        val targetClassName = intent.getStringExtra("target_activity")

        // Tentukan activity tujuan saat notifikasi di-klik
        val targetIntent = if (!targetClassName.isNullOrEmpty()) {
            try {
                val clazz = Class.forName(targetClassName)
                Intent(context, clazz).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            } catch (e: Exception) {
                Intent(context, BaseActivity::class.java)
            }
        } else {
            Intent(context, BaseActivity::class.java)
        }

        // Munculkan notifikasi menggunakan helper
        NotificationHelper.showNotification(
            context = context,
            title = title,
            message = message,
            intent = targetIntent
        )
    }
}