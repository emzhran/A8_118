package com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mohammadzachranzachary118.model.Pembayaran
import com.example.mohammadzachranzachary118.repository.PembayaranRepository
import kotlinx.coroutines.launch

class UpdatePembayaranViewModel(
    private val pmbyrn: PembayaranRepository
) : ViewModel() {

    var updatePembayaranState by mutableStateOf(UpdatePembayaranState())
        private set

    fun getPembayaranById(idPembayaran: String) {
        viewModelScope.launch {
            try {
                val pembayaran = pmbyrn.getPembayaranById(idPembayaran)
                updatePembayaranState = updatePembayaranState.copy(
                    updatePembayaranEvent = pembayaran.toUpdatePembayaranEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
                updatePembayaranState = updatePembayaranState.copy(
                    isError = true,
                    errorMessage = e.message
                )
            }
        }
    }

    fun updateUpdatePembayaranState(updatePembayaranEvent: UpdatePembayaranEvent) {
        updatePembayaranState = updatePembayaranState.copy(
            updatePembayaranEvent = updatePembayaranState.updatePembayaranEvent.copy(
                idpembayaran = updatePembayaranEvent.idpembayaran,
                idmahasiswa = updatePembayaranEvent.idmahasiswa.ifEmpty { updatePembayaranState.updatePembayaranEvent.idmahasiswa },
                jumlah = updatePembayaranEvent.jumlah,
                tanggalbayar = updatePembayaranEvent.tanggalbayar,
                statusbayar = updatePembayaranEvent.statusbayar
            )
        )
    }


    fun updatePembayaran() {
        viewModelScope.launch {
            try {
                val pembayaran = updatePembayaranState.updatePembayaranEvent.toPembayaran()
                pmbyrn.updatePembayaran(pembayaran.idpembayaran, pembayaran)
                updatePembayaranState = updatePembayaranState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                updatePembayaranState = updatePembayaranState.copy(
                    isError = true,
                    errorMessage = e.message
                )
            }
        }
    }
}

data class UpdatePembayaranState(
    val updatePembayaranEvent: UpdatePembayaranEvent = UpdatePembayaranEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdatePembayaranEvent(
    val idpembayaran: String = "",
    val idmahasiswa: String = "",
    val tanggalbayar: String = "",
    val jumlah: String = "",
    val statusbayar: String = ""
)

fun UpdatePembayaranEvent.toPembayaran(): Pembayaran = Pembayaran(
    idpembayaran = idpembayaran,
    idmahasiswa = idmahasiswa,
    tanggalbayar = tanggalbayar,
    jumlah = jumlah,
    statusbayar = statusbayar
)

fun Pembayaran.toUpdatePembayaranEvent(): UpdatePembayaranEvent = UpdatePembayaranEvent(
    idpembayaran = idpembayaran,
    idmahasiswa = idmahasiswa,
    tanggalbayar = tanggalbayar,
    jumlah = jumlah,
    statusbayar = statusbayar
)
