package com.example.mohammadzachranzachary118.ui.view.kamar

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mohammadzachranzachary118.R
import com.example.mohammadzachranzachary118.ui.customwidget.TopAppBarr
import com.example.mohammadzachranzachary118.ui.navigasi.DestinasiNavigasi
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.UpdateKamarEvent
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.UpdateKamarState
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.UpdateKamarViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateKamar: DestinasiNavigasi {
    override val route = "update_kamar"
    override val titleRes = "Update Kamar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKamarScreen(
    navigateBack: () -> Unit,
    id_kamar: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateKamarViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val kamarState = viewModel.updateKamarState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id_kamar) {
        viewModel.getKamarById(id_kamar)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                DestinasiUpdateKamar.titleRes,
                canNavigateBack = true,
                showRefresh = false,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateKamar(
            updateKamarState = kamarState,
            onKamarValueChange = viewModel::updateUpdateKamarState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKamar()
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
fun UpdateKamar(
    updateKamarState: UpdateKamarState,
    onKamarValueChange: (UpdateKamarEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputKamar(
            updateKamarEvent = updateKamarState.updateKamarEvent,
            onValueChange = onKamarValueChange,
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
fun FormInputKamar(
    updateKamarEvent: UpdateKamarEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateKamarEvent) -> Unit = {},
    enabled: Boolean = true,
    idBangunanOptions: List<String> = emptyList()
) {
    var bangunanDropdownExpanded by remember { mutableStateOf(false) }
    var statusDropdownExpanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("Terisi", "Kosong")
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateKamarEvent.nokamar,
            onValueChange = { onValueChange(updateKamarEvent.copy(nokamar = it)) },
            label = { Text("Nomor Kamar") },
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
                value = updateKamarEvent.idbangunan,
                onValueChange = {},
                label = { Text("Bangunan ID") },
                modifier = Modifier.fillMaxWidth().clickable { bangunanDropdownExpanded = true },
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
                expanded = bangunanDropdownExpanded,
                onDismissRequest = { bangunanDropdownExpanded = false }
            ) {
                idBangunanOptions.forEach { id ->
                    DropdownMenuItem(
                        text = { Text(id) },
                        onClick = {
                            onValueChange(updateKamarEvent.copy(idbangunan = id))
                            bangunanDropdownExpanded = false
                        }
                    )
                }
            }
        }
        OutlinedTextField(
            value = updateKamarEvent.kapasitas,
            onValueChange = { onValueChange(updateKamarEvent.copy(kapasitas = it)) },
            label = { Text("Kapasitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = updateKamarEvent.statuskamar,
                onValueChange = {},
                label = { Text("Status Kamar") },
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
                            onValueChange(updateKamarEvent.copy(statuskamar = status))
                            statusDropdownExpanded = false
                        }
                    )
                }
            }
        }
    }
}
