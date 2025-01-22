package com.example.mohammadzachranzachary118.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Mahasiswa(
    @SerialName("id_mahasiswa")
    val idmahasiswa : String,
    @SerialName("nama_mahasiswa")
    val nama : String,
    @SerialName("nomor_identitas")
    val nomoridentitas : String,
    val email : String,
    @SerialName("nomor_telepon")
    val nomortelepon : String,
    @SerialName("id_kamar")
    val idkamar : String
)
