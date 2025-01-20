package com.example.mohammadzachranzachary118.repository

import com.example.mohammadzachranzachary118.model.Bangunan
import com.example.mohammadzachranzachary118.service.BangunanService
import okio.IOException

interface BangunanRepository{
    suspend fun getBangunan(): List<Bangunan>
    suspend fun insertBangunan(bangunan: Bangunan)
    suspend fun updateBangunan(idbangunan: String, bangunan: Bangunan)
    suspend fun deleteBangunan(idbangunan: String)
    suspend fun getBangunanById(idbangunan: String): Bangunan
}

class NetworkBangunanRepository(private val bangunanApiService: BangunanService): BangunanRepository{
    override suspend fun insertBangunan(bangunan: Bangunan){
        bangunanApiService.insertBangunan(bangunan)
    }
    override suspend fun updateBangunan(idbangunan: String, bangunan: Bangunan){
        bangunanApiService.updateBangunan(idbangunan, bangunan)
    }

    override suspend fun deleteBangunan(idbangunan: String){
        try {
            val response = bangunanApiService.deleteBangunan(idbangunan)
            if (!response.isSuccessful){
                throw IOException("Failed to delete bangunan. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getBangunan(): List<Bangunan> = bangunanApiService.getBangunan()
    override suspend fun getBangunanById(idbangunan: String): Bangunan {
        return bangunanApiService.getBangunanById(idbangunan)
    }
}