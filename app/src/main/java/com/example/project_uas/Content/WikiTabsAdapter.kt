package com.example.project_uas.Content

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class WikiTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    // Jumlah total tab yang ada
    override fun getItemCount(): Int = 4

    // Menentukan Fragment mana yang akan ditampilkan berdasarkan posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Tab1WikiFragment()
            1 -> Tab2WikiFragment()
            2 -> Tab3WikiFragment()
            3 -> Tab4WikiFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}