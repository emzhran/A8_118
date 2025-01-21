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
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.InsertKamarEvent
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.InsertKamarState
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.InsertKamarViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertkamar: DestinasiNavigasi {
    override val route = "item_kamar"
    override val titleRes = "Form Isi Data Kamar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKamarScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertKamarViewModel = viewModel(factory = PenyediaViewModel.Factory)

){

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiInsertkamar.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerpadding->
        EntryBodyKamar(
            insertKamarState = viewModel.insertKamarState,
            onKamarValueChange = viewModel::updateInsertKamarState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertKamar()
                    navigateBack()
                }
            },
            idBangunanOptions = viewModel.idBangunanOptions,
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyKamar(
    insertKamarState: InsertKamarState,
    onKamarValueChange: (InsertKamarEvent)->Unit,
    onSaveClick:()->Unit,
    idBangunanOptions: List<String>,

    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputKamar(
            insertKamarEvent = insertKamarState.insertKamarEvent,
            onValueChange = onKamarValueChange,
            idBangunanOptions = idBangunanOptions,
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
            Text(text = "Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputKamar(
    insertKamarEvent: InsertKamarEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertKamarEvent)->Unit = {},
    enabled: Boolean = true,
    idBangunanOptions: List<String> = emptyList()
){
    var statusDropdownExpanded by remember { mutableStateOf(false) }
    var bangunanDropdownExpanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("Terisi", "Kosong")
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertKamarEvent.nokamar,
            onValueChange = {onValueChange(insertKamarEvent.copy(nokamar = it))},
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
                value = insertKamarEvent.idbangunan,
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
                            onValueChange(insertKamarEvent.copy(idbangunan = id))
                            bangunanDropdownExpanded = false
                        }
                    )
                }
            }
        }
        OutlinedTextField(
            value = insertKamarEvent.kapasitas,
            onValueChange = {onValueChange(insertKamarEvent.copy(kapasitas = it))},
            label = { Text("Kapasitas") },
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
                value = insertKamarEvent.statuskamar,
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
                            onValueChange(insertKamarEvent.copy(statuskamar = status))
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