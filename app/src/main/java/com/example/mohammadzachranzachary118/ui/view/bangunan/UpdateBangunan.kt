package com.example.mohammadzachranzachary118.ui.view.bangunan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.UpdateBangunanEvent
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.UpdateBangunanState
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.UpdateBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdate: DestinasiNavigasi {
    override val route = "update_bangunan"
    override val titleRes = "Update Bangunan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBangunanScreen(
    navigateBack: () -> Unit,
    id_bangunan: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateBangunanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val bangunanState = viewModel.updateBangunanState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_bangunan) {
        viewModel.getBangunanById(id_bangunan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBangunan(
            updateBangunanState = bangunanState,
            onBangunanValueChange = viewModel::updateUpdateBangunanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateBangunan()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun UpdateBangunan(
    updateBangunanState: UpdateBangunanState,
    onBangunanValueChange: (UpdateBangunanEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputBangunan(
            updateBangunanEvent = updateBangunanState.updateBangunanEvent,
            onValueChange = onBangunanValueChange,
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
fun FormInputBangunan(
    updateBangunanEvent: UpdateBangunanEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateBangunanEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateBangunanEvent.namabangunan,
            onValueChange = { onValueChange(updateBangunanEvent.copy(namabangunan = it)) },
            label = { Text("Nama Bangunan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = updateBangunanEvent.jumlahlantai,
            onValueChange = { onValueChange(updateBangunanEvent.copy(jumlahlantai = it)) },
            label = { Text("Jumlah Lantai") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = updateBangunanEvent.alamat,
            onValueChange = { onValueChange(updateBangunanEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
    }
}
