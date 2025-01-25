package com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mohammadzachranzachary118.model.Pembayaran
import com.example.mohammadzachranzachary118.repository.PembayaranRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailPembayaranState {
    data class Success(val pembayaran: Pembayaran) : DetailPembayaranState()
    data class Error(val message: String) : DetailPembayaranState()
    object Loading : DetailPembayaranState()
}

class DetailPembayaranViewModel(
    savedStateHandle: SavedStateHandle,
    private val pembayaran: PembayaranRepository
) : ViewModel() {
    private val idpembayaran: String = checkNotNull(savedStateHandle["id_pembayaran"])

    private val _detailPembayaranState = MutableStateFlow<DetailPembayaranState>(DetailPembayaranState.Loading)
    val detailPembayaranState: StateFlow<DetailPembayaranState> = _detailPembayaranState.asStateFlow()


    init {
        getPembayaran(idpembayaran)
    }

    fun getPembayaran(idpembayaran: String) {
        viewModelScope.launch {
            _detailPembayaranState.value = DetailPembayaranState.Loading
            _detailPembayaranState.value = try {
                val pembayaran = pembayaran.getPembayaranById(idpembayaran)
                DetailPembayaranState.Success(pembayaran)
            } catch (e: IOException) {
                DetailPembayaranState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailPembayaranState.Error("Terjadi kesalahan server")
            }
        }
    }
}
