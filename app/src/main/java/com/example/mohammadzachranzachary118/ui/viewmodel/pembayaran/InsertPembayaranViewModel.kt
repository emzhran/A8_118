package com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mohammadzachranzachary118.model.Pembayaran
import com.example.mohammadzachranzachary118.repository.MahasiswaRepository
import com.example.mohammadzachranzachary118.repository.PembayaranRepository
import kotlinx.coroutines.launch

class InsertPembayaranViewModel (
    private val pmbyrn: PembayaranRepository,
    private val mhs: MahasiswaRepository
): ViewModel(){
    var insertPembayaranState by mutableStateOf(InsertPembayaranState())
        private set


    fun updateInsertPembayaranState(insertPembayaranEvent: InsertPembayaranEvent){
        insertPembayaranState = insertPembayaranState.copy(
            insertPembayaranEvent = insertPembayaranState.insertPembayaranEvent.copy(
                idpembayaran = insertPembayaranEvent.idpembayaran,
                idmahasiswa = insertPembayaranEvent.idmahasiswa.ifEmpty { insertPembayaranState.insertPembayaranEvent.idmahasiswa },
                jumlah = insertPembayaranEvent.jumlah,
                tanggalbayar = insertPembayaranEvent.tanggalbayar,
                statusbayar = insertPembayaranEvent.statusbayar
            )
        )
    }

    fun getMahasiswaById(id_mahasiswa: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = mhs.getMahasiswaById(id_mahasiswa)
                val updatedEvent = insertPembayaranState.insertPembayaranEvent.copy(
                    idmahasiswa = mahasiswa.idmahasiswa
                )
                updateInsertPembayaranState(updatedEvent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun insertPembayaran(){
        viewModelScope.launch {
            try {
                pmbyrn.insertPembayaran(insertPembayaranState.insertPembayaranEvent.toPembayaran())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertPembayaranState(
    val insertPembayaranEvent : InsertPembayaranEvent = InsertPembayaranEvent()
)

data class InsertPembayaranEvent(
    val idpembayaran: String ="",
    val idmahasiswa: String = "",
    val jumlah: String = "",
    val tanggalbayar: String = "",
    val statusbayar: String = ""
)

fun InsertPembayaranEvent.toPembayaran(): Pembayaran = Pembayaran(
    idpembayaran = idpembayaran,
    idmahasiswa = idmahasiswa,
    jumlah = jumlah,
    tanggalbayar = tanggalbayar,
    statusbayar = statusbayar
)

fun Pembayaran.toUiStatePembayaran():InsertPembayaranState = InsertPembayaranState(
    insertPembayaranEvent = toInsertPembayaranEvent()
)

fun Pembayaran.toInsertPembayaranEvent():InsertPembayaranEvent = InsertPembayaranEvent(
    idpembayaran = idpembayaran,
    idmahasiswa = idmahasiswa,
    jumlah = jumlah,
    tanggalbayar = tanggalbayar,
    statusbayar = statusbayar
)