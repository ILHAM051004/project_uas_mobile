package com.example.project_uas.QRCode

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class TiketAPI(private val supabase: SupabaseClient) {

    suspend fun createTiket(nama: String, tanggal: String, jumlah: Int): String? {
        val newId = System.currentTimeMillis().toString()

        return try {
            val tiket = TiketInsert(
                id = newId,
                nama = nama,
                tanggal = tanggal,
                jumlah = jumlah
            )

            supabase.from("tiket").insert(tiket)  // insert object, bukan map

            newId
        } catch (e: Exception) {
            Log.e("TiketAPI", "Gagal insert tiket", e)
            null
        }
    }


    suspend fun getTiketById(id: String): TiketEntity? {
        return try {

            val result = supabase.from("tiket")
                .select {
                    filter {
                        eq("id", id)
                    }
                }

            // DECODE → jadikan list object TiketEntity
            val list = result.decodeList<TiketEntity>()

            // Ambil satu data saja
            list.firstOrNull()

        } catch (e: Exception) {
            Log.e("TiketAPI", "Gagal ambil tiket", e)
            null
        }
    }
}
