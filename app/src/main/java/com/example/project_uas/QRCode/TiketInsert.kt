package com.example.project_uas.QRCode

import kotlinx.serialization.Serializable

@Serializable
data class TiketInsert(
    val id: String,
    val nama: String,
    val tanggal: String,
    val jumlah: Int
)
