package com.example.mohammadzachranzachary118.service

import com.example.mohammadzachranzachary118.model.Kamar
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KamarService {
    // Menerima dan mengirim data dalam format JSON.
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("readkamar.php")
    suspend fun getKamar(): List<Kamar>

    @GET("read1kamar.php")
    suspend fun getKamarById(@Query("id_kamar") id_kamar: String): Kamar

    @POST("insertkamar.php")
    suspend fun insertKamar(@Body kamar: Kamar)

    @PUT("updatekamar.php")
    suspend fun updateKamar(@Query("id_kamar") id_kamar: String, @Body kamar: Kamar)

    @DELETE("deletekamar.php")
    suspend fun deleteKamar(@Query("id_kamar") id_kamar: String): retrofit2.Response<Void>
}