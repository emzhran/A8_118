package com.example.mohammadzachranzachary118.service

import com.example.mohammadzachranzachary118.model.Pembayaran
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PembayaranService {
    // Menerima dan mengirim data dalam format JSON.
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("readmapembayaran.php")
    suspend fun getPembayaran(): List<Pembayaran>

    @GET("read1pembayaran.php")
    suspend fun getPembayaranById(@Query("id_pembayaran") id_pembayaran: String): Pembayaran

    @POST("insertpembayaran.php")
    suspend fun insertPembayaran(@Body pembayaran: Pembayaran)

    @PUT("updatepembayaran.php")
    suspend fun updatePembayaran(@Query("id_pembayaran") id_pembayaran: String, @Body pembayaran: Pembayaran)

    @DELETE("deletepembayaran.php")
    suspend fun deletePembayaran(@Query("id_pembayaran") id_pembayaran: String): retrofit2.Response<Void>
}