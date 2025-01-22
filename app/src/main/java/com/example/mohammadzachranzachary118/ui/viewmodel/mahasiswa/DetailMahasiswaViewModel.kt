package com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.mohammadzachranzachary118.model.Mahasiswa
import com.example.mohammadzachranzachary118.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailMahasiswaState {
    data class Success(val mahasiswa: Mahasiswa) : DetailMahasiswaState()
    data class Error(val message: String) : DetailMahasiswaState()
    object Loading : DetailMahasiswaState()
}

class DetailMahasiswaViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
) : ViewModel() {
    private val idmahasiswa: String = checkNotNull(savedStateHandle["id_mahasiswa"])

    private val _detailUiState = MutableStateFlow<DetailMahasiswaState>(DetailMahasiswaState.Loading)
    val detailMahasiswaState: StateFlow<DetailMahasiswaState> = _detailUiState.asStateFlow()

    fun getMahasiswa(idmahasiswa: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailMahasiswaState.Loading
            _detailUiState.value = try {
                val mahasiswa = mhs.getMahasiswaById(idmahasiswa)
                DetailMahasiswaState.Success(mahasiswa)
            } catch (e: IOException) {
                DetailMahasiswaState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailMahasiswaState.Error("Terjadi kesalahan server")
            }
        }
    }
}

