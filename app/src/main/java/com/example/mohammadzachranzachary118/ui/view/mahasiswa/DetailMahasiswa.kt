package com.example.mohammadzachranzachary118.ui.view.mahasiswa

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
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.DetailMahasiswaState
import com.example.mohammadzachranzachary118.ui.viewmodel.mahasiswa.DetailMahasiswaViewModel
import com.example.mohammadzachranzachary118.ui.viewmodel.penyedia.PenyediaViewModel

object DestinasiDetailMahasiswa : DestinasiNavigasi {
    override val route = "detail_mahasiswa"
    override val titleRes = "Detail Mahasiswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMahasiswaScreen(
    navigateBack: () -> Unit,
    id_mahasiswa: String,
    modifier: Modifier = Modifier,
    viewModel: DetailMahasiswaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navController: NavHostController
) {
    val mahasiswaState by viewModel.detailMahasiswaState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getMahasiswa(id_mahasiswa)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarr(
                title = DestinasiDetailMahasiswa.titleRes,
                canNavigateBack = true,
                showRefresh = false,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("update_mahasiswa/$id_mahasiswa")
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = colorResource(R.color.primary),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Update Mahasiswa")
            }
        }
    ) { innerPadding ->
        DetailBodyMahasiswa(
            detailMahasiswaState = mahasiswaState,
            onPembayaranClick = {
                navController.navigate("insert_pembayaran/$id_mahasiswa")
            },
            onRiwayatClick = {
                navController.navigate("riwayat_pembayaran/$id_mahasiswa")
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBodyMahasiswa(
    detailMahasiswaState: DetailMahasiswaState,
    onPembayaranClick : ()->Unit,
    onRiwayatClick : ()->Unit,
    modifier: Modifier = Modifier
) {
    when (detailMahasiswaState) {
        is DetailMahasiswaState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailMahasiswaState.Error -> {
            Text(
                text = detailMahasiswaState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailMahasiswaState.Success -> {
            val mahasiswa = detailMahasiswaState.mahasiswa
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = modifier.padding(12.dp)
            ) {
                ComponentDetailMahasiswa(judul = "Nama Mahasiswa", isinya = mahasiswa.nama)
                ComponentDetailMahasiswa(judul = "Nomor Identitas", isinya = mahasiswa.nomoridentitas)
                ComponentDetailMahasiswa(judul = "Email", isinya = mahasiswa.email)
                ComponentDetailMahasiswa(judul = "Nomor Telepon", isinya = mahasiswa.nomortelepon)
                ComponentDetailMahasiswa(judul = "Kamar ID", isinya = mahasiswa.idkamar)
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onPembayaranClick,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primary)
                    )
                ) {
                    Text(text = "Tambah Pembayaran")
                }
                Button(
                    onClick = onRiwayatClick,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.primary)
                    )
                ) {
                    Text(text = "Riwayat Pembayaran")
                }
            }
        }
    }
}

@Composable
fun ComponentDetailMahasiswa(
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