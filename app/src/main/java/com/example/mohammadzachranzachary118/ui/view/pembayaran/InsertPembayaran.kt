package com.example.mohammadzachranzachary118.ui.view.pembayaran

import android.app.DatePickerDialog
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
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mohammadzachranzachary118.R
import com.example.mohammadzachranzachary118.ui.customwidget.TopAppBarr
import com.example.mohammadzachranzachary118.ui.navigasi.DestinasiNavigasi
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.InsertPembayaranEvent
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.InsertPembayaranState
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.InsertPembayaranViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.PembayaranErrorState
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DestinasiInsertPembayaran: DestinasiNavigasi {
    override val route = "insert_pembayaran"
    override val titleRes = "Form Isi Data Pembayaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPembayaranScreen(
    navigateBack:()->Unit,
    id_mahasiswa : String,
    modifier: Modifier = Modifier,
    viewModel: InsertPembayaranViewModel = viewModel(factory = PenyediaViewModel.Factory)

){

    val insertPembayaranState = viewModel.insertPembayaranState
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_mahasiswa) {
        viewModel.getMahasiswaById(id_mahasiswa)
    }

    LaunchedEffect(insertPembayaranState.snackBarMessage) {
        insertPembayaranState.snackBarMessage?.let { message->
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
                title = DestinasiInsertPembayaran.titleRes,
                canNavigateBack = true,
                showRefresh = false,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerpadding->
        EntryBodyPembayaran(
            insertPembayaranState = viewModel.insertPembayaranState,
            onPembayaranValueChange = viewModel::updateInsertPembayaranState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPembayaran()
                    if (viewModel.insertPembayaranState.isEntryValid.isValid()){
                        delay(700)
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPembayaran(
    insertPembayaranState: InsertPembayaranState,
    onPembayaranValueChange: (InsertPembayaranEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPembayaran(
            insertPembayaranEvent = insertPembayaranState.insertPembayaranEvent,
            onValueChange = onPembayaranValueChange,
            modifier = Modifier.fillMaxWidth(),
            errorState = insertPembayaranState.isEntryValid
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
fun FormInputPembayaran(
    insertPembayaranEvent: InsertPembayaranEvent,
    modifier: Modifier = Modifier,
    errorState: PembayaranErrorState = PembayaranErrorState(),
    onValueChange:(InsertPembayaranEvent)->Unit = {},
    enabled: Boolean = true
){

    var statusDropdownExpanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("Lunas", "Belum Lunas")
    var selectedDate by remember { mutableStateOf(insertPembayaranEvent.tanggalbayar) }

    val context = LocalContext.current
    val datePickerDialog = remember {
        DatePickerDialog(context, { _, year, month, dayOfMonth ->
            selectedDate = "$year/${month + 1}/$dayOfMonth"
            onValueChange(insertPembayaranEvent.copy(tanggalbayar = selectedDate))
        }, 2025, 0, 1)
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPembayaranEvent.idmahasiswa,
            onValueChange = {onValueChange(insertPembayaranEvent.copy(idmahasiswa = it))},
            label = { Text("Mahasiswa ID") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true,
            isError = errorState.idmahasiswa != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.idmahasiswa.isNullOrEmpty()) {
            Text(
                text = errorState.idmahasiswa,
                color = Color.Red
            )
        }
        OutlinedTextField(
            value = insertPembayaranEvent.tanggalbayar,
            onValueChange = { },
            label = { Text("Tanggal Pembayaran") },
            modifier = Modifier.fillMaxWidth().clickable {
                datePickerDialog.show()
            },
            enabled = false,
            singleLine = true,
            isError = errorState.tanggalbayar != null,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Calendar Icon"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.tanggalbayar.isNullOrEmpty()) {
            Text(
                text = errorState.tanggalbayar,
                color = Color.Red
            )
        }
        OutlinedTextField(
            value = insertPembayaranEvent.jumlah,
            onValueChange = {onValueChange(insertPembayaranEvent.copy(jumlah = it))},
            label = { Text("Jumlah Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            isError = errorState.jumlah != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.jumlah.isNullOrEmpty()) {
            Text(
                text = errorState.jumlah,
                color = Color.Red
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = insertPembayaranEvent.statusbayar,
                onValueChange = {},
                label = { Text("Status Pembayaran") },
                modifier = Modifier.fillMaxWidth().clickable { statusDropdownExpanded = true },
                enabled = false,
                isError = errorState.statusbayar != null,
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
            if (!errorState.statusbayar.isNullOrEmpty()) {
                Text(
                    text = errorState.statusbayar,
                    color = Color.Red
                )
            }
            DropdownMenu(
                expanded = statusDropdownExpanded,
                onDismissRequest = { statusDropdownExpanded = false }
            ) {
                statusOptions.forEach { status ->
                    DropdownMenuItem(
                        text = { Text(status) },
                        onClick = {
                            onValueChange(insertPembayaranEvent.copy(statusbayar = status))
                            statusDropdownExpanded = false
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