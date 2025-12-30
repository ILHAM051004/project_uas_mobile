package com.example.project_uas.Model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val nama: String,
    val email: String,
    val phone: String,
    val username: String,
    val password: String
)
