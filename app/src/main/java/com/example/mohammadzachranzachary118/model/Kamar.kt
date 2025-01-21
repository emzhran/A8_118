package com.example.mohammadzachranzachary118.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kamar(
    @SerialName("id_kamar")
    val idkamar : String,
    @SerialName("no_kamar")
    val nokamar : String,
    @SerialName("id_bangunan")
    val idbangunan : String,
    val kapasitas : String,
    @SerialName("status_kamar")
    val statuskamar : String
)
