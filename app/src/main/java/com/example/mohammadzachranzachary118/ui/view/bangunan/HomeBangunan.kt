package com.example.mohammadzachranzachary118.ui.view.bangunan

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
import androidx.compose.material.icons.filled.Build
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
import com.example.mohammadzachranzachary118.model.Bangunan
import com.example.mohammadzachranzachary118.ui.customwidget.TopAppBarr
import com.example.mohammadzachranzachary118.ui.navigasi.DestinasiNavigasi
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.HomeBangunanState
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.HomeBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel

object DestinasiHome: DestinasiNavigasi {
    override val route = "home_bangunan"
    override val titleRes = "Daftar Bangunan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBangunanScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    navigateToUpdate: (Bangunan) -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    viewModel: HomeBangunanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var bangunanToDelete by remember { mutableStateOf<Bangunan?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getBangunan()
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarr(
                title = DestinasiHome.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                showRefresh = true,
                onRefresh = {
                    viewModel.getBangunan()
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = navigateToItemEntry,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Bangunan"
                    )
                },
                text = {
                    Text(text = "Tambah Bangunan")
                },
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
    ) { innerPadding ->
        HomeStatusBangunan(
            homeBangunanState = viewModel.bangunanHomeState,
            retryAction = { viewModel.getBangunan() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = { bangunan ->
                bangunanToDelete = bangunan
                showDeleteConfirmationDialog = true },
            onUpdateClick = navigateToUpdate,
            onDetailClick = onDetailClick
        )
        if (showDeleteConfirmationDialog && bangunanToDelete != null) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    bangunanToDelete?.let {
                        viewModel.deletBangunan(it.idbangunan)
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
        text = { Text("Apakah anda yakin ingin menghapus data bangunan?") },
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
fun HomeStatusBangunan(
    homeBangunanState: HomeBangunanState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Bangunan) -> Unit,
    onUpdateClick: (Bangunan) -> Unit,
    onDetailClick: (String) -> Unit
) {
    when (homeBangunanState) {
        is HomeBangunanState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeBangunanState.Success -> {
            if (homeBangunanState.bangunan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Bangunan")
                }
            } else {
                BangunanLayout(
                    bangunan = homeBangunanState.bangunan,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = onDeleteClick,
                    onUpdateClick = onUpdateClick,
                    onDetailClick = {
                        onDetailClick(it.idbangunan)
                    }
                )
            }
        }
        is HomeBangunanState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun BangunanLayout(
    bangunan: List<Bangunan>,
    modifier: Modifier = Modifier,
    onDeleteClick: (Bangunan) -> Unit,
    onUpdateClick: (Bangunan) -> Unit,
    onDetailClick: (Bangunan) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(bangunan) { bgn ->
            BangunanCard(
                bangunan = bgn,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(bgn) },
                onDeleteClick = onDeleteClick,
                onUpdateClick = onUpdateClick
            )
        }
    }
}

@Composable
fun BangunanCard(
    bangunan: Bangunan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Bangunan) -> Unit,
    onUpdateClick: (Bangunan) -> Unit
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
                Icon(painter = painterResource(R.drawable.icons8_building_100), contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = bangunan.namabangunan,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Place, contentDescription = "")
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = bangunan.alamat,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onUpdateClick(bangunan) }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { onDeleteClick(bangunan) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Hapus")
                }
            }
        }
    }
}