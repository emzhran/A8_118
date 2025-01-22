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
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.UpdateMahasiswaEvent
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.UpdateMahasiswaState
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.UpdateMahasiswaViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateMahasiswa: DestinasiNavigasi {
    override val route = "update_mahasiswa"
    override val titleRes = "Update Mahasiswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMahasiswaScreen(
    navigateBack: () -> Unit,
    id_mahasiswa: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateMahasiswaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val mahasiswaState = viewModel.updateMahasiswaState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(id_mahasiswa) {
        viewModel.getMahasiswaId(id_mahasiswa)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                DestinasiUpdateMahasiswa.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateMahasiswa(
            updateMahasiswaState = mahasiswaState,
            onMahasiswaValueChange = viewModel::updateUpdateMahasiswaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateMahasiswa()
                    navigateBack()
                }
            },
            idKamarOptions = viewModel.idKamarOptions,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun UpdateMahasiswa(
    updateMahasiswaState: UpdateMahasiswaState,
    onMahasiswaValueChange: (UpdateMahasiswaEvent) -> Unit,
    onSaveClick: () -> Unit,
    idKamarOptions: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputMahasiswa(
            updateMahasiswaEvent = updateMahasiswaState.updateMahasiswaEvent,
            onValueChange = onMahasiswaValueChange,
            idKamarOptions = idKamarOptions,
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
fun FormInputMahasiswa(
    updateMahasiswaEvent: UpdateMahasiswaEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateMahasiswaEvent) -> Unit = {},
    enabled: Boolean = true,
    idKamarOptions: List<String> = emptyList()
) {
    var kamarDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateMahasiswaEvent.nama,
            onValueChange = { onValueChange(updateMahasiswaEvent.copy(nama = it)) },
            label = { Text("Nama Mahasiswa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = updateMahasiswaEvent.nomoridentitas,
            onValueChange = { onValueChange(updateMahasiswaEvent.copy(nomoridentitas = it)) },
            label = { Text("Nomor Identitas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = updateMahasiswaEvent.email,
            onValueChange = { onValueChange(updateMahasiswaEvent.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary)
            )
        )
        OutlinedTextField(
            value = updateMahasiswaEvent.nomortelepon,
            onValueChange = { onValueChange(updateMahasiswaEvent.copy(nomortelepon = it)) },
            label = { Text("Nomor Telepon") },
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
                value = updateMahasiswaEvent.idkamar,
                onValueChange = {},
                label = { Text("Kamar ID") },
                modifier = Modifier.fillMaxWidth().clickable { kamarDropdownExpanded = true },
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
                expanded = kamarDropdownExpanded,
                onDismissRequest = { kamarDropdownExpanded = false }
            ) {
                idKamarOptions.forEach { id ->
                    DropdownMenuItem(
                        text = { Text(id) },
                        onClick = {
                            onValueChange(updateMahasiswaEvent.copy(idkamar = id))
                            kamarDropdownExpanded = false
                        }
                    )
                }
            }
        }
    }
}