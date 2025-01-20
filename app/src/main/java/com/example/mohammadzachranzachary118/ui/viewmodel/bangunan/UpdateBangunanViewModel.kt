package com.example.mohammadzachranzachary118.ui.viewmodel.bangunan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mohammadzachranzachary118.model.Bangunan
import com.example.mohammadzachranzachary118.repository.BangunanRepository
import kotlinx.coroutines.launch

class UpdateBangunanViewModel(
    private val bgn: BangunanRepository
) : ViewModel() {

    var updateBangunanState by mutableStateOf(UpdateBangunanState())
        private set

    fun updateUpdateBangunanState(updateBangunanEvent: UpdateBangunanEvent) {
        updateBangunanState = updateBangunanState.copy(updateBangunanEvent = updateBangunanEvent)
    }

    fun getBangunanById(id_bangunan: String) {
        viewModelScope.launch {
            try {
                val bangunan = bgn.getBangunanById(id_bangunan)
                updateBangunanState = UpdateBangunanState(updateBangunanEvent = bangunan.toUpdateBangunanEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                updateBangunanState = UpdateBangunanState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateBangunan() {
        viewModelScope.launch {
            try {
                val bangunan = updateBangunanState.updateBangunanEvent.toBangunan()
                bgn.updateBangunan(bangunan.idbangunan, bangunan)
                updateBangunanState = updateBangunanState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                updateBangunanState = updateBangunanState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateBangunanState(
    val updateBangunanEvent: UpdateBangunanEvent = UpdateBangunanEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateBangunanEvent(
    val idbangunan: String = "",
    val namabangunan: String = "",
    val jumlahlantai: String = "",
    val alamat: String = ""
)

fun UpdateBangunanEvent.toBangunan(): Bangunan = Bangunan(
    idbangunan = idbangunan,
    namabangunan = namabangunan,
    jumlahlantai = jumlahlantai,
    alamat = alamat
)

fun Bangunan.toUpdateBangunanEvent(): UpdateBangunanEvent = UpdateBangunanEvent(
    idbangunan = idbangunan,
    namabangunan = namabangunan,
    jumlahlantai = jumlahlantai,
    alamat = alamat
)