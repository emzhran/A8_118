package com.example.mohammadzachranzachary118

import android.app.Application
import com.example.mohammadzachranzachary118.repository.AppContainer
import com.example.mohammadzachranzachary118.repository.DormitoryContainer

class DormitoryApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = DormitoryContainer()
    }
}