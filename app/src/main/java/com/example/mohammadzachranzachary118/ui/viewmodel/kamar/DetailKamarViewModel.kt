package com.example.mohammadzachranzachary118.ui.viewmodel.kamar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mohammadzachranzachary118.model.Kamar
import com.example.mohammadzachranzachary118.repository.KamarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

sealed class DetailKamarState {
    data class Success(val kamar: Kamar) : DetailKamarState()
    data class Error(val message: String) : DetailKamarState()
    object Loading : DetailKamarState()
}

class DetailKamarViewModel(
    savedStateHandle: SavedStateHandle,
    private val kmr: KamarRepository
) : ViewModel() {
    private val idkamar: String = checkNotNull(savedStateHandle["id_kamar"])

    private val _detailKamarState = MutableStateFlow<DetailKamarState>(DetailKamarState.Loading)
    val detailKamarState: StateFlow<DetailKamarState> = _detailKamarState.asStateFlow()

    init {
        getKamar(idkamar)
    }

    fun getKamar(idkamar: String) {
        viewModelScope.launch {
            _detailKamarState.value = DetailKamarState.Loading
            _detailKamarState.value = try {
                val kamar = kmr.getKamarById(idkamar)
                DetailKamarState.Success(kamar)
            } catch (e: IOException) {
                DetailKamarState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailKamarState.Error("Terjadi kesalahan server")
            }
        }
    }
}
