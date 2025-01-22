package com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mohammadzachranzachary118.model.Kamar
import com.example.mohammadzachranzachary118.model.Mahasiswa
import com.example.mohammadzachranzachary118.repository.KamarRepository
import com.example.mohammadzachranzachary118.repository.MahasiswaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UpdateMahasiswaViewModel(
    private val mhs: MahasiswaRepository,
    private val kmr: KamarRepository
) : ViewModel() {

    var updateMahasiswaState by mutableStateOf(UpdateMahasiswaState())
        private set

    val kamarList: StateFlow<List<Kamar>> = flow {
        emit(kmr.getKamar())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var idKamarOptions by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        fetchKamarList()
    }

    private fun fetchKamarList() {
        viewModelScope.launch {
            try {
                val kamarData = kmr.getKamar()
                idKamarOptions = kamarData
                    .filter { it.statuskamar == "Kosong" }
                    .map { it.idkamar }
            } catch (e: Exception) {
                idKamarOptions = emptyList()
            }
        }
    }

    fun updateUpdateMahasiswaState(updateMahasiswaEvent: UpdateMahasiswaEvent) {
        updateMahasiswaState = updateMahasiswaState.copy(updateMahasiswaEvent = updateMahasiswaEvent)
    }

    fun getMahasiswaId(id_mahasiswa: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = mhs.getMahasiswaById(id_mahasiswa)
                updateMahasiswaState = UpdateMahasiswaState(updateMahasiswaEvent = mahasiswa.toUpdateMhsEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                updateMahasiswaState = UpdateMahasiswaState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateMahasiswa() {
        viewModelScope.launch {
            try {
                val mahasiswa = updateMahasiswaState.updateMahasiswaEvent.toMhs()
                mahasiswa.idmahasiswa?.let { id ->
                    mhs.updateMahasiswa(id, mahasiswa) // Hanya dieksekusi jika idmahasiswa tidak null
                    updateMahasiswaState = updateMahasiswaState.copy(isSuccess = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                updateMahasiswaState = updateMahasiswaState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateMahasiswaState(
    val updateMahasiswaEvent: UpdateMahasiswaEvent = UpdateMahasiswaEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateMahasiswaEvent(
    val idmahasiswa: String = "",
    val nama: String = "",
    val nomoridentitas: String = "",
    val email: String = "",
    val nomortelepon: String = "",
    val idkamar: String = ""
)

fun UpdateMahasiswaEvent.toMhs(): Mahasiswa = Mahasiswa(
    idmahasiswa = idmahasiswa,
    nama = nama,
    nomoridentitas = nomoridentitas,
    email = email,
    nomortelepon = nomortelepon,
    idkamar = idkamar
)

fun Mahasiswa.toUpdateMhsEvent(): UpdateMahasiswaEvent = UpdateMahasiswaEvent(
    idmahasiswa = idmahasiswa,
    nama = nama,
    nomoridentitas = nomoridentitas,
    email = email,
    nomortelepon = nomortelepon,
    idkamar = idkamar
)
