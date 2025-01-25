package com.example.mohammadzachranzachary118.ui.view.pembayaran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mohammadzachranzachary118.R
import com.example.mohammadzachranzachary118.ui.customwidget.TopAppBarr
import com.example.mohammadzachranzachary118.ui.navigasi.DestinasiNavigasi
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.DetailPembayaranState
import com.example.mohammadzachranzachary118.ui.viewmodel.pembayaran.DetailPembayaranViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel

object DestinasiDetailPembayaran : DestinasiNavigasi {
    override val route = "detail_pembayaran"
    override val titleRes = "Detail Pembayaran"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPembayaranScreen(
    navigateBack: () -> Unit,
    id_pembayaran: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPembayaranViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navController: NavHostController
) {
    val pembayaranState by viewModel.detailPembayaranState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect (Unit){
        viewModel.getPembayaran(id_pembayaran)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiDetailPembayaran.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("update_pembayaran/$id_pembayaran")
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Update Pembayaran")
            }
        }
    ) { innerPadding ->
        DetailBodyPembayaran(
            detailPembayaranState = pembayaranState,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBodyPembayaran(
    detailPembayaranState: DetailPembayaranState,
    modifier: Modifier = Modifier
) {
    when (detailPembayaranState) {
        is DetailPembayaranState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailPembayaranState.Error -> {
            Text(
                text = detailPembayaranState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailPembayaranState.Success -> {
            val pembayaran = detailPembayaranState.pembayaran
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = modifier.padding(12.dp)
            ) {
                ComponentDetailPembayaran(judul = "Mahasiswa ID", isinya = pembayaran.idmahasiswa)
                ComponentDetailPembayaran(judul = "Tanggal Pembayaran", isinya = pembayaran.tanggalbayar)
                ComponentDetailPembayaran(judul = "Jumlah Pembayaran", isinya = pembayaran.jumlah)
                ComponentDetailPembayaran(judul = "Status Pembayaran", isinya = pembayaran.statusbayar)
            }
        }
    }
}

@Composable
fun ComponentDetailPembayaran(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = isinya,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
