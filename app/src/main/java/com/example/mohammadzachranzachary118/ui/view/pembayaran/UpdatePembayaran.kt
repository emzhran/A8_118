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
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.UpdatePembayaranEvent
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.UpdatePembayaranState
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.UpdatePembayaranViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePembayaran: DestinasiNavigasi {
    override val route = "update_pembayaran"
    override val titleRes = "Update Pembayaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePembayaranScreen(
    navigateBack: () -> Unit,
    id_pembayaran: String,
    modifier: Modifier = Modifier,
    viewModel: UpdatePembayaranViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val pembayaranState = viewModel.updatePembayaranState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id_pembayaran) {
        viewModel.getPembayaranById(id_pembayaran)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                DestinasiUpdatePembayaran.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdatePembayaran(
            updatePembayaranState = pembayaranState,
            onPembayaranValueChange = viewModel::updateUpdatePembayaranState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePembayaran()
                    navigateBack()
                    showDialog = true
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
    if (showDialog) {
        LaunchedEffect(showDialog) {
            kotlinx.coroutines.delay(5000)
        }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = colorResource(R.color.primary),
            title = {
                Text(
                    "Berhasil",
                    color = Color.White
                )
            },
            text = {
                Text(
                    "Berhasil update data.",
                    color = Color.White
                )
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.primary))
                ) {
                    Text("OK", color = Color.White)
                }
            }
        )
    }
}


@Composable
fun UpdatePembayaran(
    updatePembayaranState: UpdatePembayaranState,
    onPembayaranValueChange: (UpdatePembayaranEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPembayaran(
            updatePembayaranEvent = updatePembayaranState.updatePembayaranEvent,
            onValueChange = onPembayaranValueChange,
            modifier = Modifier.fillMaxWidth()
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
            Text(text = "Update")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPembayaran(
    updatePembayaranEvent: UpdatePembayaranEvent,
    modifier: Modifier = Modifier,
    onValueChange:(UpdatePembayaranEvent)->Unit = {},
    enabled: Boolean = true
){

    var statusDropdownExpanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("Lunas", "Belum Lunas")
    var selectedDate by remember { mutableStateOf(updatePembayaranEvent.tanggalbayar) }

    val context = LocalContext.current
    val datePickerDialog = remember {
        DatePickerDialog(context, { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            onValueChange(updatePembayaranEvent.copy(tanggalbayar = selectedDate))
        }, 2025, 0, 1)
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updatePembayaranEvent.idmahasiswa,
            onValueChange = {onValueChange(updatePembayaranEvent.copy(idmahasiswa = it))},
            label = { Text("Mahasiswa ID") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = updatePembayaranEvent.tanggalbayar,
            onValueChange = {},
            label = { Text("Tanggal Pembayaran") },
            modifier = Modifier.fillMaxWidth().clickable {
                datePickerDialog.show()
            },
            enabled = false,
            singleLine = true,
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
        OutlinedTextField(
            value = updatePembayaranEvent.jumlah,
            onValueChange = {onValueChange(updatePembayaranEvent.copy(jumlah = it))},
            label = { Text("Jumlah Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = updatePembayaranEvent.statusbayar,
                onValueChange = {},
                label = { Text("Status Pembayaran") },
                modifier = Modifier.fillMaxWidth().clickable { statusDropdownExpanded = true },
                enabled = false,
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
            DropdownMenu(
                expanded = statusDropdownExpanded,
                onDismissRequest = { statusDropdownExpanded = false }
            ) {
                statusOptions.forEach { status ->
                    DropdownMenuItem(
                        text = { Text(status) },
                        onClick = {
                            onValueChange(updatePembayaranEvent.copy(statusbayar = status))
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