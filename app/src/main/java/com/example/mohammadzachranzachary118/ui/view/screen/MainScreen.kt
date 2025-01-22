package com.example.mohammadzachranzachary118.ui.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mohammadzachranzachary118.R
import com.example.mohammadzachranzachary118.ui.navigasi.DestinasiNavigasi

object MainScreen: DestinasiNavigasi {
    override val route = "menu_dormitory"
    override val titleRes = ""
}

@Composable
fun MainMenuScreen(
    onNavigateToMahasiswa: () -> Unit,
    onNavigateToBangunan: () -> Unit,
    onNavigateToKamar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.primary))
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Asrama Mahasiswa",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.umy),
                contentDescription = "",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 24.dp)
            )
            Button(
                onClick = { onNavigateToMahasiswa() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.menu)
                )
            ) {
                Text(text = "Mahasiswa",
                    color = Color.White)
            }
            Button(
                onClick = { onNavigateToBangunan() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.menu)
                )
            ) {
                Text(text = "Bangunan",
                    color = Color.White)
            }
            Button(
                onClick = { onNavigateToKamar() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.menu)
                )
            ) {
                Text(text = "Kamar",
                    color = Color.White)
            }
        }
    }
}
