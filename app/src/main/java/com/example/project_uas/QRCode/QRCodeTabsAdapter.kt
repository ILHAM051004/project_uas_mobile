package com.example.project_uas.QRCode

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class QRCodeTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    // Jumlah total tab yang ada
    override fun getItemCount(): Int = 2

    // Menentukan Fragment mana yang akan ditampilkan berdasarkan posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabQrcodeFragment()
            1 -> TabScanFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}