package com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mohammadzachranzachary118.model.Pembayaran
import com.example.mohammadzachranzachary118.repository.PembayaranRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class RiwayatPembayaranState{
    data class Success(val pembayaran: List<Pembayaran>): RiwayatPembayaranState()
    object Error: RiwayatPembayaranState()
    object Loading: RiwayatPembayaranState()
}

class RiwayatPembayaranViewModel(private val pmbyrn: PembayaranRepository) : ViewModel() {
    var riwayatPembayaranState: RiwayatPembayaranState by mutableStateOf(RiwayatPembayaranState.Loading)
        private set


    fun getRiwayat(id_mahasiswa: String) {
        viewModelScope.launch {
            riwayatPembayaranState = RiwayatPembayaranState.Loading
            riwayatPembayaranState = try {
                RiwayatPembayaranState.Success(pmbyrn.getPembayaran().filter { it.idmahasiswa == id_mahasiswa })
            } catch (e: IOException) {
                RiwayatPembayaranState.Error
            } catch (e: HttpException) {
                RiwayatPembayaranState.Error
            }
        }
    }

    fun deletePembayaran(id_mahasiswa: String) {
        viewModelScope.launch {
            try {
                pmbyrn.deletePembayaran(id_mahasiswa)
                getRiwayat(id_mahasiswa)
            } catch (e: IOException) {
                RiwayatPembayaranState.Error
            } catch (e: HttpException) {
                RiwayatPembayaranState.Error
            }
        }
    }
}
