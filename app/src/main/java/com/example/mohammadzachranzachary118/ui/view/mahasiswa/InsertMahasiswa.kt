package com.example.mohammadzachranzachary118.ui.view.mahasiswa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mohammadzachranzachary118.R
import com.example.mohammadzachranzachary118.ui.customwidget.TopAppBarr
import com.example.mohammadzachranzachary118.ui.navigasi.DestinasiNavigasi
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.InsertMahasiswaEvent
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.InsertMahasiswaState
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.InsertMahasiswaViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.MahasiswaErrorState
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DestinasiInsertMahasiswa: DestinasiNavigasi {
    override val route = "item_mahasiswa"
    override val titleRes = "Form Isi Data Mahasiswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMahasiswaScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertMahasiswaViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val insertMahasiswaState = viewModel.insertMahasiswaState
    val snackBarHostState = remember {SnackbarHostState()}
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(insertMahasiswaState.snackBarMessage) {
        insertMahasiswaState.snackBarMessage?.let { message->
            coroutineScope.launch {
                snackBarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiInsertMahasiswa.titleRes,
                canNavigateBack = true,
                showRefresh = false,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerpadding->
        EntryBodyMahasiswa(
            insertMahasiswaState = viewModel.insertMahasiswaState,
            onMahasiswaValueChange = viewModel::updateInsertMahasiswaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMahasiswa()
                    if (viewModel.insertMahasiswaState.isEntryValid.isValid()){
                        delay(700)
                        navigateBack()
                    }
                }
            },
            idKamarOptions = viewModel.idKamarOptions,
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyMahasiswa(
    insertMahasiswaState: InsertMahasiswaState,
    onMahasiswaValueChange: (InsertMahasiswaEvent)->Unit,
    onSaveClick:()->Unit,
    idKamarOptions: List<String>,

    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputMahasiswa(
            insertMahasiswaEvent = insertMahasiswaState.insertMahasiswaEvent,
            onValueChange = onMahasiswaValueChange,
            idKamarOptions = idKamarOptions,
            modifier = Modifier.fillMaxWidth(),
            errorState = insertMahasiswaState.isEntryValid
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White
            )
        ) {
            Text(text = "Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputMahasiswa(
    insertMahasiswaEvent: InsertMahasiswaEvent,
    modifier: Modifier = Modifier,
    errorState: MahasiswaErrorState = MahasiswaErrorState(),
    onValueChange:(InsertMahasiswaEvent)->Unit = {},
    enabled: Boolean = true,
    idKamarOptions: List<String> = emptyList()
){
    var kamarDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertMahasiswaEvent.nama,
            onValueChange = {onValueChange(insertMahasiswaEvent.copy(nama = it))},
            label = { Text("Nama Mahasiswa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = errorState.nama != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.nama.isNullOrEmpty()) {
            Text(
                text = errorState.nama,
                color = Color.Red
            )
        }

        OutlinedTextField(
            value = insertMahasiswaEvent.nomoridentitas,
            onValueChange = {onValueChange(insertMahasiswaEvent.copy(nomoridentitas = it))},
            label = { Text("Nomor Identitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            isError = errorState.nomoridentitas != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.nomoridentitas.isNullOrEmpty()) {
            Text(
                text = errorState.nomoridentitas,
                color = Color.Red
            )
        }

        OutlinedTextField(
            value = insertMahasiswaEvent.email,
            onValueChange = {onValueChange(insertMahasiswaEvent.copy(email = it))},
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            isError = errorState.email != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.email.isNullOrEmpty()) {
            Text(
                text = errorState.email,
                color = Color.Red
            )
        }

        OutlinedTextField(
            value = insertMahasiswaEvent.nomortelepon,
            onValueChange = {onValueChange(insertMahasiswaEvent.copy(nomortelepon = it))},
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            isError = errorState.nomortelepon != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.nomortelepon.isNullOrEmpty()) {
            Text(
                text = errorState.nomortelepon,
                color = Color.Red
            )
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = insertMahasiswaEvent.idkamar,
                onValueChange = {},
                label = { Text("Kamar ID") },
                modifier = Modifier.fillMaxWidth().clickable { kamarDropdownExpanded = true },
                enabled = false,
                isError = errorState.idkamar != null,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.primary)
                )
            )
            if (!errorState.idkamar.isNullOrEmpty()) {
                Text(
                    text = errorState.idkamar,
                    color = Color.Red
                )
            }

            DropdownMenu(
                expanded = kamarDropdownExpanded,
                onDismissRequest = { kamarDropdownExpanded = false }
            ) {
                idKamarOptions.forEach { id ->
                    DropdownMenuItem(
                        text = { Text(id) },
                        onClick = {
                            onValueChange(insertMahasiswaEvent.copy(idkamar = id))
                            kamarDropdownExpanded = false
                        }
                    )
                }
            }
        }
        if (enabled){
            Text(
                text = "Isi Semua Data",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 6.dp,
            modifier = Modifier.padding(6.dp)
        )
    }
}