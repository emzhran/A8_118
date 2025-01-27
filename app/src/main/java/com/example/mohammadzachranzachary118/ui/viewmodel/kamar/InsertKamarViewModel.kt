package com.example.mohammadzachranzachary118.ui.viewmodel.kamar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mohammadzachranzachary118.model.Bangunan
import com.example.mohammadzachranzachary118.model.Kamar
import com.example.mohammadzachranzachary118.repository.BangunanRepository
import com.example.mohammadzachranzachary118.repository.KamarRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InsertKamarViewModel (
    private val kamar: KamarRepository,
    private val bgn: BangunanRepository
): ViewModel(){
    var insertKamarState by mutableStateOf(InsertKamarState())
        private set

    val bangunanList: StateFlow<List<Bangunan>> = flow {
        emit(bgn.getBangunan())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var idBangunanOptions by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        fetchBangunanList()
    }

    private fun fetchBangunanList() {
        viewModelScope.launch {
            try {
                val bangunanData = bgn.getBangunan()
                idBangunanOptions = bangunanData
                    .mapNotNull { it.idbangunan.toIntOrNull() }
                    .sorted() // mengurut secara ascending
                    .map { it.toString() }
            } catch (e: Exception) {
                idBangunanOptions = emptyList()
            }
        }
    }


    fun updateInsertKamarState(insertKamarEvent: InsertKamarEvent){
        insertKamarState = InsertKamarState(insertKamarEvent = insertKamarEvent)
    }

    suspend fun insertKamar(){
        val currentEvent = insertKamarState.insertKamarEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    kamar.insertKamar(currentEvent.toKamar())
                    insertKamarState = insertKamarState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        isEntryValid = KamarErrorState()
                    )
                } catch (e: Exception) {
                    insertKamarState = insertKamarState.copy(snackBarMessage = "Data gagal disimpan")
                }
            }
        }else{
            insertKamarState = insertKamarState.copy(snackBarMessage = "Input tidak valid, periksa kembali data")
        }
    }

    fun resetSnackBarMessage(){
        insertKamarState = insertKamarState.copy(snackBarMessage = null)
    }

    private fun validateFields(): Boolean{
        val event = insertKamarState.insertKamarEvent
        val errorState = KamarErrorState(
            nokamar =  if (event.nokamar.isNotEmpty()) null else "Nomor kamar tidak boleh kosong",
            idbangunan = if (event.idbangunan.isNotEmpty()) null else "Bangunan ID tidak boleh kosong",
            kapasitas = if (event.kapasitas.isNotEmpty()) null else "Kapasitas tidak boleh kosong",
            statuskamar = if (event.statuskamar.isNotEmpty()) null else "Status kamar tidak boleh kosong"
        )
        insertKamarState = insertKamarState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
}



data class InsertKamarState(
    val insertKamarEvent : InsertKamarEvent = InsertKamarEvent(),
    val isEntryValid : KamarErrorState = KamarErrorState(),
    val snackBarMessage : String? = null
)

data class KamarErrorState(
    val nokamar: String?= null,
    val idbangunan: String?= null,
    val kapasitas: String?= null,
    val statuskamar: String?=null
){
    fun isValid():Boolean{
        return  nokamar == null && idbangunan == null
                && kapasitas == null && statuskamar == null
    }
}

data class InsertKamarEvent(
    val idkamar: String ="",
    val nokamar: String = "",
    val idbangunan: String = "",
    val kapasitas: String = "",
    val statuskamar: String = ""
)

fun InsertKamarEvent.toKamar(): Kamar = Kamar(
    idkamar = idkamar,
    nokamar = nokamar,
    idbangunan = idbangunan,
    kapasitas = kapasitas,
    statuskamar = statuskamar
)

fun Kamar.toUiStateKamar():InsertKamarState = InsertKamarState(
    insertKamarEvent =  toInsertKamarEvent()
)

fun Kamar.toInsertKamarEvent():InsertKamarEvent = InsertKamarEvent(
    idkamar = idkamar,
    nokamar = nokamar,
    idbangunan = idbangunan,
    kapasitas = kapasitas,
    statuskamar = statuskamar
)