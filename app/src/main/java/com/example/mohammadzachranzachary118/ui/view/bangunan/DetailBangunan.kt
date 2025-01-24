package com.example.mohammadzachranzachary118.ui.view.bangunan

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mohammadzachranzachary118.ui.customwidget.TopAppBarr
import com.example.mohammadzachranzachary118.ui.navigasi.DestinasiNavigasi
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.DetailBangunanState
import com.example.mohammadzachranzachary118.ui.viewmodel.bangunan.DetailBangunanViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiDetailBangunan : DestinasiNavigasi {
    override val route = "detail_bangunan"
    override val titleRes = "Detail Bangunan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBangunanScreen(
    navigateBack: () -> Unit,
    id_bangunan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailBangunanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navController: NavHostController
) {
    val bangunanState by viewModel.detailBangunanState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect (Unit){
        viewModel.getBangunan(id_bangunan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiDetailBangunan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        DetailBodyBangunan(
            detailBangunanState = bangunanState,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBodyBangunan(
    detailBangunanState: DetailBangunanState,
    modifier: Modifier = Modifier
) {
    when (detailBangunanState) {
        is DetailBangunanState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailBangunanState.Error -> {
            Text(
                text = detailBangunanState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailBangunanState.Success -> {
            val bangunan = detailBangunanState.bgn
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = modifier.padding(12.dp)
            ) {
                ComponentDetailBangunan(judul = "Bangunan ID", isinya = bangunan.idbangunan)
                ComponentDetailBangunan(judul = "Nama Bangunan", isinya = bangunan.namabangunan)
                ComponentDetailBangunan(judul = "Alamat", isinya = bangunan.alamat)
                ComponentDetailBangunan(judul = "Jumlah Lantai", isinya = bangunan.jumlahlantai)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ComponentDetailBangunan(
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
