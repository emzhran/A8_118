package com.example.mohammadzachranzachary118.ui.viewmodel.penyedia

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mohammadzachranzachary118.DormitoryApplication
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.DetailBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.HomeBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.InsertBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.UpdateBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.DetailKamarViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.HomeKamarViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.InsertKamarViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.UpdateKamarViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.DetailMahasiswaViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.HomeMahasiswaViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.InsertMahasiswaViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.UpdateMahasiswaViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.InsertPembayaranViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeBangunanViewModel(dormitoryApp().container.bangunanRepository) }
        initializer { InsertBangunanViewModel(dormitoryApp().container.bangunanRepository) }
        initializer { UpdateBangunanViewModel(dormitoryApp().container.bangunanRepository) }
        initializer { DetailBangunanViewModel(createSavedStateHandle(),
            dormitoryApp().container.bangunanRepository) }
        initializer { HomeKamarViewModel(dormitoryApp().container.kamarRepository) }
        initializer { InsertKamarViewModel(dormitoryApp().container.kamarRepository,dormitoryApp().container.bangunanRepository) }
        initializer { UpdateKamarViewModel(dormitoryApp().container.kamarRepository,dormitoryApp().container.bangunanRepository) }
        initializer { DetailKamarViewModel(createSavedStateHandle(),
            dormitoryApp().container.kamarRepository) }
        initializer { HomeMahasiswaViewModel(dormitoryApp().container.mahasiswaRepository) }
        initializer { InsertMahasiswaViewModel(dormitoryApp().container.mahasiswaRepository,dormitoryApp().container.kamarRepository) }
        initializer { UpdateMahasiswaViewModel(dormitoryApp().container.mahasiswaRepository,dormitoryApp().container.kamarRepository) }
        initializer { DetailMahasiswaViewModel(createSavedStateHandle(),
            dormitoryApp().container.mahasiswaRepository) }
        initializer { InsertPembayaranViewModel(dormitoryApp().container.pembayaranRepository,dormitoryApp().container.mahasiswaRepository) }
        }
    }

fun CreationExtras.dormitoryApp(): DormitoryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as DormitoryApplication)