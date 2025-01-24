package com.example.mohammadzachranzachary118.ui.viewmodel.bangunan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mohammadzachranzachary118.model.Bangunan
import com.example.mohammadzachranzachary118.repository.BangunanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailBangunanState {
    data class Success(val bgn: Bangunan) : DetailBangunanState()
    data class Error(val message: String) : DetailBangunanState()
    object Loading : DetailBangunanState()
}

class DetailBangunanViewModel(
    savedStateHandle: SavedStateHandle,
    private val bgn: BangunanRepository
) : ViewModel() {
    private val idbangunan: String = checkNotNull(savedStateHandle["id_bangunan"])

    private val _detailBangunanState = MutableStateFlow<DetailBangunanState>(DetailBangunanState.Loading)
    val detailBangunanState: StateFlow<DetailBangunanState> = _detailBangunanState.asStateFlow()

    init {
        getBangunan(idbangunan)
    }

    fun getBangunan(idbangunan: String) {
        viewModelScope.launch {
            _detailBangunanState.value = DetailBangunanState.Loading
            _detailBangunanState.value = try {
                val bangunan = bgn.getBangunanById(idbangunan)
                DetailBangunanState.Success(bangunan)
            } catch (e: IOException) {
                DetailBangunanState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailBangunanState.Error("Terjadi kesalahan server")
            }
        }
    }
}