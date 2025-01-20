package com.example.mohammadzachranzachary118.ui.viewmodel.penyedia

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mohammadzachranzachary118.DormitoryApplication
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.HomeBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.InsertBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.UpdateBangunanViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeBangunanViewModel(dormitoryApp().container.bangunanRepository) }
        initializer { InsertBangunanViewModel(dormitoryApp().container.bangunanRepository) }
        initializer { UpdateBangunanViewModel(dormitoryApp().container.bangunanRepository) }
    }
}

fun CreationExtras.dormitoryApp(): DormitoryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as DormitoryApplication)