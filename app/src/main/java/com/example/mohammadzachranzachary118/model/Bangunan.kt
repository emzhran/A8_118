package com.example.mohammadzachranzachary118.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Bangunan(
    @SerialName("id_bangunan")
    val idbangunan : String,
    @SerialName("nama_bangunan")
    val namabangunan : String,
    @SerialName("jumlah_lantai")
    val jumlahlantai : String,
    val alamat : String
)
