package com.example.mohammadzachranzachary118.ui.view.kamar

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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.DetailKamarState
import com.example.mohammadzachranzachary118.ui.viewmodel.kamar.DetailKamarViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiDetailKamar : DestinasiNavigasi {
    override val route = "detail_kamar"
    override val titleRes = "Detail Kamar"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKamarScreen(
    navigateBack: () -> Unit,
    id_kamar: String,
    modifier: Modifier = Modifier,
    viewModel: DetailKamarViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navController: NavHostController
) {
    val kamarState by viewModel.detailKamarState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect (Unit){
        viewModel.getKamar(id_kamar)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiDetailKamar.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        DetailBodyKamar(
            detailKamarState = kamarState,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBodyKamar(
    detailKamarState: DetailKamarState,
    modifier: Modifier = Modifier
) {
    when (detailKamarState) {
        is DetailKamarState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailKamarState.Error -> {
            Text(
                text = detailKamarState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailKamarState.Success -> {
            val kamar = detailKamarState.kamar
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = modifier.padding(12.dp)
            ) {
                ComponentDetailKamar(judul = "Nomor Kamar", isinya = kamar.nokamar)
                ComponentDetailKamar(judul = "Bangunan ID", isinya = kamar.idbangunan)
                ComponentDetailKamar(judul = "Kapasitas", isinya = kamar.kapasitas)
                ComponentDetailKamar(judul = "Status Kamar", isinya = kamar.statuskamar)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ComponentDetailKamar(
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
