package com.example.mohammadzachranzachary118.repository

import com.example.mohammadzachranzachary118.model.Mahasiswa
import com.example.mohammadzachranzachary118.service.MahasiswaService
import okio.IOException

interface MahasiswaRepository{
    suspend fun getMahasiswa(): List<Mahasiswa>
    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)
    suspend fun updateMahasiswa(idmahasiswa: String, mahasiswa: Mahasiswa)
    suspend fun deleteMahasiswa(idmahasiswa: String)
    suspend fun getMahasiswaById(idmahasiswa: String): Mahasiswa
}

class NetworkMahasiswaRepository(private val mahasiswaApiService: MahasiswaService): MahasiswaRepository{
    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa){
        mahasiswaApiService.insertMahasiswa(mahasiswa)
    }
    override suspend fun updateMahasiswa(idmahasiswa: String, mahasiswa: Mahasiswa){
        mahasiswaApiService.updateMahasiswa(idmahasiswa, mahasiswa)
    }

    override suspend fun deleteMahasiswa(idmahasiswa: String){
        try {
            val response = mahasiswaApiService.deleteMahasiswa(idmahasiswa)
            if (!response.isSuccessful){
                throw IOException("Failed to delete mahasiswa. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getMahasiswa(): List<Mahasiswa> = mahasiswaApiService.getMahasiswa()
    override suspend fun getMahasiswaById(idmahasiswa: String): Mahasiswa {
        return mahasiswaApiService.getMahasiswaById(idmahasiswa)
    }
}