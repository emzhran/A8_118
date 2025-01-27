package com.example.mohammadzachranzachary118.ui.viewmodel.bangunan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mohammadzachranzachary118.model.Bangunan
import com.example.mohammadzachranzachary118.repository.BangunanRepository
import kotlinx.coroutines.launch

class InsertBangunanViewModel (private val bgn: BangunanRepository): ViewModel(){
    var insertBangunanState by mutableStateOf(InsertBangunanState())
        private set

    fun updateInsertBangunanState(insertBangunanEvent: InsertBangunanEvent){
        insertBangunanState = InsertBangunanState(insertBangunanEvent = insertBangunanEvent)
    }

    suspend fun insertBangunan(){
        val currentEvent = insertBangunanState.insertBangunanEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    bgn.insertBangunan(currentEvent.toBangunan())
                    insertBangunanState = insertBangunanState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        isEntryValid = BangunanErrorState()
                    )
                } catch (e: Exception) {
                    insertBangunanState = insertBangunanState.copy(snackBarMessage = "Data gagal disimpan")
                }
            }
        }else{
            insertBangunanState = insertBangunanState.copy(snackBarMessage = "Input tidak valid, periksa kembali data")
        }
    }

    fun resetSnackBarMessage(){
        insertBangunanState = insertBangunanState.copy(snackBarMessage = null)
    }

    private fun validateFields(): Boolean{
        val event = insertBangunanState.insertBangunanEvent
        val errorState = BangunanErrorState(
            namabangunan =  if (event.namabangunan.isNotEmpty()) null else "Nama bangunan tidak boleh kosong",
            jumlahlantai = if (event.jumlahlantai.isNotEmpty()) null else "Jumlah lantai tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong"
        )
        insertBangunanState = insertBangunanState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
}


data class InsertBangunanState(
    val insertBangunanEvent : InsertBangunanEvent = InsertBangunanEvent(),
    val isEntryValid : BangunanErrorState = BangunanErrorState(),
    val snackBarMessage : String? = null
)

data class BangunanErrorState(
    val namabangunan: String?= null,
    val jumlahlantai: String?= null,
    val alamat:  String?=null
){
    fun isValid():Boolean{
        return  namabangunan == null && jumlahlantai == null && alamat == null
    }
}

data class InsertBangunanEvent(
    val idbangunan: String ="",
    val namabangunan: String = "",
    val jumlahlantai: String = "",
    val alamat: String = ""
)

fun InsertBangunanEvent.toBangunan(): Bangunan = Bangunan(
    idbangunan = idbangunan,
    namabangunan = namabangunan,
    jumlahlantai = jumlahlantai,
    alamat = alamat
)

fun Bangunan.toUiStateBangunan():InsertBangunanState = InsertBangunanState(
    insertBangunanEvent =  toInsertBangunanEvent()
)

fun Bangunan.toInsertBangunanEvent():InsertBangunanEvent = InsertBangunanEvent(
    idbangunan = idbangunan,
    namabangunan = namabangunan,
    jumlahlantai = jumlahlantai,
    alamat = alamat
)