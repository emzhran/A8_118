package com.example.mohammadzachranzachary118.ui.customwidget


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mohammadzachranzachary118.R

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    onKamarClick: () -> Unit = {},
    onBangunanClick: () -> Unit = {},
    onPembayaranClick: () -> Unit = {}
) {
    BottomAppBar(
        containerColor = colorResource(R.color.primary),
        contentColor = Color.White,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = onKamarClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Kamar",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Kamar",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = onBangunanClick
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Bangunan",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Bangunan",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = onPembayaranClick
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Pembayaran",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Pembayaran",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
