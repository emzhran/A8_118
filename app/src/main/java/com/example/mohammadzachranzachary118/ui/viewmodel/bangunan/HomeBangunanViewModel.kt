package com.example.mohammadzachranzachary118.ui.viewmodel.bangunan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mohammadzachranzachary118.model.Bangunan
import com.example.mohammadzachranzachary118.repository.BangunanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeBangunanState{
    data class Success(val bangunan: List<Bangunan>): HomeBangunanState()
    object Error: HomeBangunanState()
    object Loading: HomeBangunanState()
}

class HomeBangunanViewModel (private val bgn: BangunanRepository): ViewModel(){
    var bangunanHomeState: HomeBangunanState by mutableStateOf(HomeBangunanState.Loading)
        private set

    init {
        getBangunan()
    }

    fun getBangunan(){
        viewModelScope.launch {
            bangunanHomeState = HomeBangunanState.Loading
            bangunanHomeState = try {
                HomeBangunanState.Success(bgn.getBangunan())
            }catch (e: IOException){
                HomeBangunanState.Error
            }catch (e: HttpException){
                HomeBangunanState.Error
            }
        }
    }

    fun deletBangunan(id_bangunan:String){
        viewModelScope.launch {
            try {
                bgn.deleteBangunan(id_bangunan)
            }catch (e: IOException){
                HomeBangunanState.Error
            }catch (e: HttpException){
                HomeBangunanState.Error
            }
        }
    }
}