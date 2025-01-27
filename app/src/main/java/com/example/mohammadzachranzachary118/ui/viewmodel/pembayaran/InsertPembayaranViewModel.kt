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
        val currentEvent = insertPembayaranState.insertPembayaranEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pmbyrn.insertPembayaran(currentEvent.toPembayaran())
                    insertPembayaranState = insertPembayaranState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        isEntryValid = PembayaranErrorState()
                    )
                } catch (e: Exception) {
                    insertPembayaranState = insertPembayaranState.copy(snackBarMessage = "Data gagal disimpan")
                }
            }
        }else{
            insertPembayaranState = insertPembayaranState.copy(snackBarMessage = "Input tidak valid, periksa kembali data")
        }
    }

    fun resetSnackBarMessage(){
        insertPembayaranState = insertPembayaranState.copy(snackBarMessage = null)
    }

    private fun validateFields(): Boolean{
        val event = insertPembayaranState.insertPembayaranEvent
        val errorState = PembayaranErrorState(
            idmahasiswa =  if (event.idmahasiswa.isNotEmpty()) null else "Mahasiswa ID tidak boleh kosong",
            jumlah = if (event.jumlah.isNotEmpty()) null else "Jumlah pembayaran tidak boleh kosong",
            tanggalbayar = if (event.tanggalbayar.isNotEmpty()) null else "Tanggal pembayaran tidak boleh kosong",
            statusbayar = if (event.statusbayar.isNotEmpty()) null else "Status pembayaran tidak boleh kosong"
        )
        insertPembayaranState = insertPembayaranState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
}


data class InsertPembayaranState(
    val insertPembayaranEvent : InsertPembayaranEvent = InsertPembayaranEvent(),
    val isEntryValid : PembayaranErrorState = PembayaranErrorState(),
    val snackBarMessage : String? = null
)


data class PembayaranErrorState(
    val idmahasiswa: String?= null,
    val jumlah: String?= null,
    val tanggalbayar: String?= null,
    val statusbayar: String?= null
){
    fun isValid():Boolean{
        return  idmahasiswa == null && jumlah == null
                && tanggalbayar == null && statusbayar == null
    }
}

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