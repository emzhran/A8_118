package com.example.mohammadzachranzachary118.ui.viewmodel.kamar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mohammadzachranzachary118.model.Kamar
import com.example.mohammadzachranzachary118.repository.KamarRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeKamarState{
    data class Success(val kamar: List<Kamar>): HomeKamarState()
    object Error: HomeKamarState()
    object Loading: HomeKamarState()
}

class HomeKamarViewModel (private val kmr: KamarRepository): ViewModel(){
    var kamarHomeState: HomeKamarState by mutableStateOf(HomeKamarState.Loading)
        private set

    init {
        getKamar()
    }

    fun getKamar(){
        viewModelScope.launch {
            kamarHomeState = HomeKamarState.Loading
            kamarHomeState = try {
                HomeKamarState.Success(kmr.getKamar())
            }catch (e: IOException){
                HomeKamarState.Error
            }catch (e: HttpException){
                HomeKamarState.Error
            }
        }
    }

}