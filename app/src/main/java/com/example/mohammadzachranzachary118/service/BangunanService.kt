package com.example.mohammadzachranzachary118.service

import com.example.mohammadzachranzachary118.model.Bangunan
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BangunanService {
    // Menerima dan mengirim data dalam format JSON.
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("readbangunan.php")
    suspend fun getBangunan(): List<Bangunan>

    @GET("read1bangunan.php")
    suspend fun getBangunanById(@Query("id_bangunan") id_bangunan: String): Bangunan

    @POST("insertbangunan.php")
    suspend fun insertBangunan(@Body bangunan: Bangunan)

    @PUT("updatebangunan.php")
    suspend fun updateBangunan(@Query("id_bangunan") id_bangunan: String, @Body bangunan: Bangunan)

    @DELETE("deletebangunan.php")
    suspend fun deleteBangunan(@Query("id_bangunan") id_bangunan: String): retrofit2.Response<Void>
}