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
        viewModelScope.launch {
            try {
                bgn.insertBangunan(insertBangunanState.insertBangunanEvent.toBangunan())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertBangunanState(
    val insertBangunanEvent : InsertBangunanEvent = InsertBangunanEvent()
)

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