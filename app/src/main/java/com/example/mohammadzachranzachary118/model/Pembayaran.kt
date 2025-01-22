package com.example.mohammadzachranzachary118.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pembayaran(
    @SerialName("id_pembayaran")
    val idpembayaran : String,
    @SerialName("id_mahasiswa")
    val idmahasiswa : String,
    val jumlah : String,
    @SerialName("tanggal_pembayaran")
    val tanggalbayar : String,
    @SerialName("status_pembayaran")
    val statusbayar : String
)
