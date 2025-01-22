package com.example.mohammadzachranzachary118.service

import com.example.mohammadzachranzachary118.model.Mahasiswa
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MahasiswaService {
    // Menerima dan mengirim data dalam format JSON.
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("readmahasiswa.php")
    suspend fun getMahasiswa(): List<Mahasiswa>

    @GET("read1mahasiswa.php")
    suspend fun getMahasiswaById(@Query("id_mahasiswa") id_mahasiswa: String): Mahasiswa

    @POST("insertmahasiswa.php")
    suspend fun insertMahasiswa(@Body mahasiswa: Mahasiswa)

    @PUT("updatemahasiswa.php/{id_mahasiswa}")
    suspend fun updateMahasiswa(@Path("id_mahasiswa") id_mahasiswa: String, @Body mahasiswa: Mahasiswa)

    @DELETE("deletemahasiswa.php/{id_mahasiswa}")
    suspend fun deleteMahasiswa(@Path("id_mahasiswa") id_mahasiswa: String): retrofit2.Response<Void>
}