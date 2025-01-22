package com.example.mohammadzachranzachary118.repository

import com.example.mohammadzachranzachary118.model.Pembayaran
import com.example.mohammadzachranzachary118.service.MahasiswaService
import com.example.mohammadzachranzachary118.service.PembayaranService
import okio.IOException

interface PembayaranRepository{
    suspend fun getPembayaran(): List<Pembayaran>
    suspend fun insertPembayaran(pembayaran: Pembayaran)
    suspend fun updatePembayaran(idpembayaran: String, pembayaran: Pembayaran)
    suspend fun deletePembayaran(idpembayaran: String)
    suspend fun getPembayaranById(idpembayaran: String): Pembayaran
}

class NetworkPembayaranRepository(private val pembayaranApiService: PembayaranService): PembayaranRepository{
    override suspend fun insertPembayaran(pembayaran: Pembayaran){
        pembayaranApiService.insertPembayaran(pembayaran)
    }
    override suspend fun updatePembayaran(idpembayaran: String, pembayaran: Pembayaran){
        pembayaranApiService.updatePembayaran(idpembayaran, pembayaran)
    }

    override suspend fun deletePembayaran(idpembayaran: String){
        try {
            val response = pembayaranApiService.deletePembayaran(idpembayaran)
            if (!response.isSuccessful){
                throw IOException("Failed to delete pembayaran. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getPembayaran(): List<Pembayaran> = pembayaranApiService.getPembayaran()
    override suspend fun getPembayaranById(idpembayaran: String): Pembayaran {
        return pembayaranApiService.getPembayaranById(idpembayaran)
    }
}