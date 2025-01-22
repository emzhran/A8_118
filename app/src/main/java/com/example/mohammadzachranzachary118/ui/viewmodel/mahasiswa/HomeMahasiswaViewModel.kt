package com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mohammadzachranzachary118.model.Mahasiswa
import com.example.mohammadzachranzachary118.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeMahasiswaState{
    data class Success(val mahasiswa: List<Mahasiswa>): HomeMahasiswaState()
    object Error: HomeMahasiswaState()
    object Loading: HomeMahasiswaState()
}

class HomeMahasiswaViewModel (private val mhs: MahasiswaRepository): ViewModel(){
    var mahasiwaHomeState: HomeMahasiswaState by mutableStateOf(HomeMahasiswaState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs(){
        viewModelScope.launch {
            mahasiwaHomeState = HomeMahasiswaState.Loading
            mahasiwaHomeState = try {
                HomeMahasiswaState.Success(mhs.getMahasiswa())
            }catch (e: IOException){
                HomeMahasiswaState.Error
            }catch (e: HttpException){
                HomeMahasiswaState.Error
            }
        }
    }

    fun deleteMhs(id_mahasiswa:String){
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(id_mahasiswa)
                getMhs()
            }catch (e: IOException){
                HomeMahasiswaState.Error
            }catch (e: HttpException){
                HomeMahasiswaState.Error
            }
        }
    }
}