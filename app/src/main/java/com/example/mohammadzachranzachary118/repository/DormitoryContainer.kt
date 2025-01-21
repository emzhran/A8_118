package com.example.mohammadzachranzachary118.repository


import com.example.mohammadzachranzachary118.service.BangunanService
import com.example.mohammadzachranzachary118.service.KamarService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bangunanRepository: BangunanRepository
    val kamarRepository: KamarRepository
}
class DormitoryContainer : AppContainer{
    private val baseUrl = "http://10.0.2.2/pamTA/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val bangunanService: BangunanService by lazy { retrofit.create(BangunanService::class.java) }
    private val kamarService: KamarService by lazy { retrofit.create(KamarService::class.java) }
    override val bangunanRepository: BangunanRepository by lazy { NetworkBangunanRepository(bangunanService) }
    override val kamarRepository: KamarRepository by lazy { NetworkKamarRepository(kamarService) }
}