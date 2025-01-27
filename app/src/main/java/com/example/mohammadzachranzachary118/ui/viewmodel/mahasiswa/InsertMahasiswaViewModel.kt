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

    fun insertMahasiswa(){
        val currentEvent = insertMahasiswaState.insertMahasiswaEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    mhs.insertMahasiswa(currentEvent.toMahasiswa())
                    insertMahasiswaState = insertMahasiswaState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        isEntryValid = MahasiswaErrorState()
                    )
                } catch (e: Exception) {
                    insertMahasiswaState = insertMahasiswaState.copy(snackBarMessage = "Data gagal disimpan")
                }
            }
        }else{
            insertMahasiswaState = insertMahasiswaState.copy(snackBarMessage = "Input tidak valid, periksa kembali data")
        }
    }
    fun resetSnackBarMessage(){
        insertMahasiswaState = insertMahasiswaState.copy(snackBarMessage = null)
    }

    private fun validateFields(): Boolean{
        val event = insertMahasiswaState.insertMahasiswaEvent
        val errorState = MahasiswaErrorState(
            nama = if (event.nama.isNotEmpty()) null else "Nama mahasiswa tidak boleh kosong",
            nomoridentitas = if (event.nomoridentitas.isNotEmpty()) null else "Nomor identitas tidak boleh kosong",
            email = if (event.email.isNotEmpty()) null else "Email tidak boleh kosong",
            nomortelepon = if (event.nomortelepon.isNotEmpty()) null else "Nomor telepon tidak boleh kosong",
            idkamar = if (event.idkamar.isNotEmpty()) null else "Kamar ID tidak boleh kosong"
        )
        insertMahasiswaState = insertMahasiswaState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
}


data class InsertMahasiswaState(
    val insertMahasiswaEvent : InsertMahasiswaEvent = InsertMahasiswaEvent(),
    val isEntryValid : MahasiswaErrorState = MahasiswaErrorState(),
    val snackBarMessage : String? = null
)

data class MahasiswaErrorState(
    val nama: String? = null,
    val nomoridentitas: String? = null,
    val email: String? = null,
    val nomortelepon: String? = null,
    val idkamar: String? = null
){
    fun isValid():Boolean{
        return  nama == null && nomoridentitas == null && email == null
                && nomortelepon == null && idkamar == null
    }
}

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