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
        viewModelScope.launch {
            try {
                kamar.insertKamar(insertKamarState.insertKamarEvent.toKamar())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}



data class InsertKamarState(
    val insertKamarEvent : InsertKamarEvent = InsertKamarEvent()
)

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