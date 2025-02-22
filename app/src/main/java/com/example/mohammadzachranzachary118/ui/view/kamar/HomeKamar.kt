package com.example.mohammadzachranzachary118.ui.view.kamar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mohammadzachranzachary118.R
import com.example.mohammadzachranzachary118.model.Kamar
import com.example.mohammadzachranzachary118.ui.customwidget.TopAppBarr
import com.example.mohammadzachranzachary118.ui.navigasi.DestinasiNavigasi
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.HomeKamarState
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.HomeKamarViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel

object DestinasiHomeKamar: DestinasiNavigasi {
    override val route = "home_kamar"
    override val titleRes = "Daftar kamar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKamarScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    onNavigateToUpdate: (Kamar)-> Unit,
    onDetailClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeKamarViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var kamarToDelete by remember { mutableStateOf<Kamar?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getKamar()
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarr(
                title = DestinasiHomeKamar.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                showRefresh = true,
                onRefresh = {
                    viewModel.getKamar()
                },

            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = navigateToItemEntry,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Kamar"
                    )
                },
                text = {
                    Text(text = "Tambah Kamar")
                },
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { innerPadding ->
        HomeStatusKamar(
            homeKamarState = viewModel.kamarHomeState,
            retryAction = { viewModel.getKamar() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onUpdateClick = onNavigateToUpdate,
            onDeleteClick = {kamar ->
                kamarToDelete = kamar
                showDeleteConfirmationDialog = true
            }
        )
        if (showDeleteConfirmationDialog && kamarToDelete != null) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    kamarToDelete?.let {
                        viewModel.deletKamar(it.idkamar)
                    }
                    showDeleteConfirmationDialog = false
                },
                onDeleteCancel = {
                    showDeleteConfirmationDialog = false
                }
            )
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Text("Delete Data",
                fontWeight = FontWeight.Bold)},
        text = { Text("Apakah anda yakin ingin menghapus data kamar?") },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onDeleteCancel,
                colors = ButtonDefaults.textButtonColors(containerColor = colorResource(R.color.primary),
                    contentColor = Color.White)){
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDeleteConfirm,
                colors = ButtonDefaults.textButtonColors(containerColor = colorResource(R.color.primary),
                    contentColor = Color.White)) {
                Text(text = "Yes")
            }
        }
    )
}


@Composable
fun HomeStatusKamar(
    homeKamarState: HomeKamarState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onUpdateClick: (Kamar) -> Unit,
    onDeleteClick: (Kamar) -> Unit
) {
    when (homeKamarState) {
        is HomeKamarState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeKamarState.Success -> {
            if (homeKamarState.kamar.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kamar")
                }
            } else {
                KamarLayout(
                    kamar = homeKamarState.kamar,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idkamar)
                    },
                    onUpdateClick = onUpdateClick,
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomeKamarState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.connection_loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction:()->Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction,
            colors =  ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.primary)
            )) {
            Text(stringResource(R.string.retry))
        }
    }
}


@Composable
fun KamarLayout(
    kamar: List<Kamar>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kamar) -> Unit,
    onUpdateClick: (Kamar) -> Unit,
    onDeleteClick: (Kamar) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kamar) { kmr ->
            KamarCard(
                kamar = kmr,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kmr) },
                onUpdateClick = onUpdateClick,
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun KamarCard(
    kamar: Kamar,
    onUpdateClick : (Kamar) ->Unit,
    onDeleteClick : (Kamar) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(R.drawable.icons8_room_100), contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = kamar.nokamar,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = kamar.statuskamar,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onUpdateClick(kamar) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDeleteClick(kamar) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
        }
    }
}