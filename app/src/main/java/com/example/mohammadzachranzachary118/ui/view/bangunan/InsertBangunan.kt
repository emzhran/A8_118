package com.example.mohammadzachranzachary118.ui.view.bangunan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.BangunanErrorState
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.InsertBangunanEvent
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.InsertBangunanState
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.InsertBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DestinasiEntry: DestinasiNavigasi {
    override val route = "item_bangunan"
    override val titleRes = "Form Isi Data Bangunan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBangunanScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertBangunanViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val insertBangunanState = viewModel.insertBangunanState
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(insertBangunanState.snackBarMessage) {
        insertBangunanState.snackBarMessage?.let { message->
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
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                showRefresh = false,
                navigateUp = navigateBack
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerpadding->
        EntryBodyBangunan(
            insertBangunanState = viewModel.insertBangunanState,
            onBangunanValueChange = viewModel::updateInsertBangunanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertBangunan()
                    if (viewModel.insertBangunanState.isEntryValid.isValid()){
                        delay(200)
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
fun EntryBodyBangunan(
    insertBangunanState: InsertBangunanState,
    onBangunanValueChange: (InsertBangunanEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputBangunan(
            insertBangunanEvent = insertBangunanState.insertBangunanEvent,
            onValueChange = onBangunanValueChange,
            modifier = Modifier.fillMaxWidth(),
            errorState = insertBangunanState.isEntryValid
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
fun FormInputBangunan(
    insertBangunanEvent: InsertBangunanEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertBangunanEvent)->Unit = {},
    errorState: BangunanErrorState = BangunanErrorState(),
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertBangunanEvent.namabangunan,
            onValueChange = {onValueChange(insertBangunanEvent.copy(namabangunan = it))},
            label = { Text("Nama Bangunan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = errorState.namabangunan != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.namabangunan.isNullOrEmpty()) {
            Text(
                text = errorState.namabangunan,
                color = Color.Red
            )
        }
        OutlinedTextField(
            value = insertBangunanEvent.jumlahlantai,
            onValueChange = {onValueChange(insertBangunanEvent.copy(jumlahlantai = it))},
            label = { Text("Jumlah Lantai") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            isError = errorState.jumlahlantai != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.jumlahlantai.isNullOrEmpty()) {
            Text(
                text = errorState.jumlahlantai,
                color = Color.Red
            )
        }
        OutlinedTextField(
            value = insertBangunanEvent.alamat,
            onValueChange = {onValueChange(insertBangunanEvent.copy(alamat = it))},
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            isError = errorState.alamat != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        if (!errorState.alamat.isNullOrEmpty()) {
            Text(
                text = errorState.alamat,
                color = Color.Red
            )
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