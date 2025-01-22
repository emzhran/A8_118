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

class InsertMahasiswaViewModel (
    private val mhs: MahasiswaRepository,
    private val kmr: KamarRepository
): ViewModel(){
    var insertMahasiswaState by mutableStateOf(InsertMahasiswaState())
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
                    .mapNotNull { it.idkamar.toIntOrNull() }
                    .sorted() // mengurut secara ascending
                    .map { it.toString() }
            } catch (e: Exception) {
                idKamarOptions = emptyList()
            }
        }
    }

    fun updateInsertMahasiswaState(insertMahasiswaEvent: InsertMahasiswaEvent){
        insertMahasiswaState = InsertMahasiswaState(insertMahasiswaEvent = insertMahasiswaEvent)
    }

    suspend fun insertMahasiswa(){
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(insertMahasiswaState.insertMahasiswaEvent.toMahasiswa())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertMahasiswaState(
    val insertMahasiswaEvent : InsertMahasiswaEvent = InsertMahasiswaEvent()
)

data class InsertMahasiswaEvent(
    val idmahasiswa: String ="",
    val nama: String = "",
    val nomoridentitas: String = "",
    val email: String = "",
    val nomortelepon: String = "",
    val idkamar: String = ""
)

fun InsertMahasiswaEvent.toMahasiswa(): Mahasiswa = Mahasiswa(
    idmahasiswa = idmahasiswa,
    nama = nama,
    nomoridentitas = nomoridentitas,
    email = email,
    nomortelepon = nomortelepon,
    idkamar = idkamar
)

fun Mahasiswa.toUiStateMahasiswa():InsertMahasiswaState = InsertMahasiswaState(
    insertMahasiswaEvent = toInsertMahasiswaEvent()
)

fun Mahasiswa.toInsertMahasiswaEvent():InsertMahasiswaEvent = InsertMahasiswaEvent(
    idmahasiswa = idmahasiswa,
    nama = nama,
    nomoridentitas = nomoridentitas,
    email = email,
    nomortelepon = nomortelepon,
    idkamar = idkamar
)