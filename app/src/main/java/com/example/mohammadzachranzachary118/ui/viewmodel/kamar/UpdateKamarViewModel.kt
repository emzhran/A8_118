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

class UpdateKamarViewModel(
    private val kmr: KamarRepository,
    private val bgn: BangunanRepository
) : ViewModel() {

    var updateKamarState by mutableStateOf(UpdateKamarState())
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
                idBangunanOptions = bangunanData.map { it.idbangunan }
            } catch (e: Exception) {
                idBangunanOptions = emptyList()
            }
        }
    }

    fun updateUpdateKamarState(updateKamarEvent: UpdateKamarEvent) {
        updateKamarState = updateKamarState.copy(updateKamarEvent = updateKamarEvent)
    }

    fun getKamarById(id_kamar: String) {
        viewModelScope.launch {
            try {
                val kamar = kmr.getKamarById(id_kamar)
                updateKamarState = UpdateKamarState(updateKamarEvent = kamar.toUpdateKamarEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                updateKamarState = UpdateKamarState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateKamar() {
        viewModelScope.launch {
            try {
                val kamar = updateKamarState.updateKamarEvent.toKamar()
                kmr.updateKamar(kamar.idkamar, kamar)
                updateKamarState = updateKamarState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                updateKamarState = updateKamarState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateKamarState(
    val updateKamarEvent: UpdateKamarEvent = UpdateKamarEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateKamarEvent(
    val idkamar: String ="",
    val nokamar: String = "",
    val idbangunan: String = "",
    val kapasitas: String = "",
    val statuskamar: String = ""
)

fun UpdateKamarEvent.toKamar(): Kamar = Kamar(
    idkamar = idkamar,
    nokamar = nokamar,
    idbangunan = idbangunan,
    kapasitas = kapasitas,
    statuskamar = statuskamar
)

fun Kamar.toUpdateKamarEvent(): UpdateKamarEvent = UpdateKamarEvent(
    idkamar = idkamar,
    nokamar = nokamar,
    idbangunan = idbangunan,
    kapasitas = kapasitas,
    statuskamar = statuskamar
)