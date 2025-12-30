package com.example.project_uas.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveLoginSession(nama: String, email: String, phone: String) {
        val editor = prefs.edit()
        editor.putBoolean("isLoggedIn", true)
        editor.putString("nama", nama)
        editor.putString("email", email)
        editor.putString("phone", phone)
        editor.apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("isLoggedIn", false)
    fun getNama(): String? = prefs.getString("nama", "")
    fun getEmail(): String? = prefs.getString("email", "")
    fun getPhone(): String? = prefs.getString("phone", "")

    fun logout() {
        prefs.edit().clear().apply()
    }
}