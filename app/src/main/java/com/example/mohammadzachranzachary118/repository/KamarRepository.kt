package com.example.mohammadzachranzachary118.repository

import com.example.mohammadzachranzachary118.model.Kamar
import com.example.mohammadzachranzachary118.service.KamarService
import okio.IOException

interface KamarRepository{
    suspend fun getKamar(): List<Kamar>
    suspend fun insertKamar(kamar: Kamar)
    suspend fun updateKamar(idkamar: String, kamar: Kamar)
    suspend fun deleteKamar(idkamar: String)
    suspend fun getKamarById(idkamar: String): Kamar
}

class NetworkKamarRepository(private val kamarApiService: KamarService): KamarRepository{
    override suspend fun insertKamar(kamar: Kamar){
        kamarApiService.insertKamar(kamar)
    }
    override suspend fun updateKamar(idkamar: String, kamar: Kamar){
        kamarApiService.updateKamar(idkamar, kamar)
    }

    override suspend fun deleteKamar(idkamar: String){
        try {
            val response = kamarApiService.deleteKamar(idkamar)
            if (!response.isSuccessful){
                throw IOException("Failed to delete kamar. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getKamar(): List<Kamar> = kamarApiService.getKamar()
    override suspend fun getKamarById(idkamar: String): Kamar {
        return kamarApiService.getKamarById(idkamar)
    }
}