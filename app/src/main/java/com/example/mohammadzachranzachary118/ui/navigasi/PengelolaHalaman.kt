package com.example.mohammadzachranzachary118.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mohammadzachranzachary118.ui.view.bangunan.DestinasiDetailBangunan
import com.example.mohammadzachranzachary118.ui.view.bangunan.DestinasiEntry
import com.example.mohammadzachranzachary118.ui.view.bangunan.DestinasiHome
import com.example.mohammadzachranzachary118.ui.view.bangunan.DestinasiUpdate
import com.example.mohammadzachranzachary118.ui.view.bangunan.DetailBangunanScreen
import com.example.mohammadzachranzachary118.ui.view.bangunan.EntryBangunanScreen
import com.example.mohammadzachranzachary118.ui.view.bangunan.HomeBangunanScreen
import com.example.mohammadzachranzachary118.ui.view.bangunan.UpdateBangunanScreen
import com.example.mohammadzachranzachary118.ui.view.kamar.DestinasiDetailKamar
import com.example.mohammadzachranzachary118.ui.view.kamar.DestinasiHomeKamar
import com.example.mohammadzachranzachary118.ui.view.kamar.DestinasiInsertkamar
import com.example.mohammadzachranzachary118.ui.view.kamar.DestinasiUpdateKamar
import com.example.mohammadzachranzachary118.ui.view.kamar.DetailKamarScreen
import com.example.mohammadzachranzachary118.ui.view.kamar.EntryKamarScreen
import com.example.mohammadzachranzachary118.ui.view.kamar.HomeKamarScreen
import com.example.mohammadzachranzachary118.ui.view.kamar.UpdateKamarScreen
import com.example.mohammadzachranzachary118.ui.view.mahasiswa.DestinasiDetailMahasiswa
import com.example.mohammadzachranzachary118.ui.view.mahasiswa.DestinasiHomeMahasiswa
import com.example.mohammadzachranzachary118.ui.view.mahasiswa.DestinasiInsertMahasiswa
import com.example.mohammadzachranzachary118.ui.view.mahasiswa.DestinasiUpdateMahasiswa
import com.example.mohammadzachranzachary118.ui.view.mahasiswa.DetailMahasiswaScreen
import com.example.mohammadzachranzachary118.ui.view.mahasiswa.EntryMahasiswaScreen
import com.example.mohammadzachranzachary118.ui.view.mahasiswa.HomeMahasiswaScreen
import com.example.mohammadzachranzachary118.ui.view.mahasiswa.UpdateMahasiswaScreen
import com.example.mohammadzachranzachary118.ui.view.pembayaran.DestinasiHomePembayaran
import com.example.mohammadzachranzachary118.ui.view.pembayaran.DestinasiInsertPembayaran
import com.example.mohammadzachranzachary118.ui.view.pembayaran.EntryPembayaranScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeMahasiswa.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeBangunanScreen(
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToUpdate = { bangunan ->
                    navController.navigate("${DestinasiUpdate.route}/${bangunan.idbangunan}")
                },
                onDetailClick = {id_bangunan->
                    navController.navigate("${DestinasiDetailBangunan.route}/$id_bangunan")
                }
            )
        }
        composable(DestinasiEntry.route) {
            EntryBangunanScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "${DestinasiUpdate.route}/{id_bangunan}",
            arguments = listOf(navArgument("id_bangunan") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_bangunan = backStackEntry.arguments?.getString("id_bangunan") ?: return@composable
            UpdateBangunanScreen(
                id_bangunan = id_bangunan,
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = "${DestinasiDetailBangunan.route}/{id_bangunan}",
            arguments = listOf(navArgument("id_bangunan"){type = NavType.StringType})
        ){backStackEntry ->
            val id_bangunan =backStackEntry.arguments?.getString("id_bangunan") ?: return@composable
            DetailBangunanScreen(
                id_bangunan = id_bangunan,
                navigateBack = {navController.navigateUp()},
                navController = navController
            )
        }
        composable(DestinasiHomeKamar.route) {
            HomeKamarScreen(
                onDetailClick = {id_kamar ->
                    navController.navigate("${DestinasiDetailKamar.route}/$id_kamar")
                },
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiInsertkamar.route) },
                onNavigateToUpdate = { kamar ->
                    navController.navigate("${DestinasiUpdateKamar.route}/${kamar.idkamar}")
                }
            )
        }
        composable(
            route = "${DestinasiDetailKamar.route}/{id_kamar}",
            arguments = listOf(navArgument("id_kamar") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_kamar = backStackEntry.arguments?.getString("id_kamar") ?: return@composable
            DetailKamarScreen(
                id_kamar = id_kamar,
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(DestinasiInsertkamar.route) {
            EntryKamarScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeKamar.route) {
                        popUpTo(DestinasiHomeKamar.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "${DestinasiUpdateKamar.route}/{id_kamar}",
            arguments = listOf(navArgument("id_kamar") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_kamar = backStackEntry.arguments?.getString("id_kamar") ?: return@composable
            UpdateKamarScreen(
                id_kamar = id_kamar,
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(DestinasiHomeMahasiswa.route) {
            HomeMahasiswaScreen(
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiInsertMahasiswa.route) },
                onDetailClick = { id_mahasiswa ->
                    navController.navigate("${DestinasiDetailMahasiswa.route}/$id_mahasiswa")
                },
                onNavigateToBangunan = {navController.navigate(DestinasiHome.route)},
                onNavigateToKamar = {navController.navigate(DestinasiHomeKamar.route)},
                onNavigateToPembayaran = {navController.navigate(DestinasiHomePembayaran.route)}
            )
        }
        composable(
            route = "${DestinasiInsertMahasiswa.route}",
        ) {
            EntryMahasiswaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeMahasiswa.route) {
                        popUpTo(DestinasiHomeMahasiswa.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "${DestinasiDetailMahasiswa.route}/{id_mahasiswa}",
            arguments = listOf(navArgument("id_mahasiswa") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_mahasiswa = backStackEntry.arguments?.getString("id_mahasiswa") ?: return@composable
            DetailMahasiswaScreen(
                id_mahasiswa = id_mahasiswa,
                navigateBack = { navController.navigateUp() },
                navController = navController
            )
        }
        composable(
            route = "${DestinasiUpdateMahasiswa.route}/{id_mahasiswa}",
            arguments = listOf(navArgument("id_mahasiswa") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_mahasiswa = backStackEntry.arguments?.getString("id_mahasiswa") ?: return@composable
            UpdateMahasiswaScreen(
                id_mahasiswa = id_mahasiswa,
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = "${DestinasiInsertPembayaran.route}/{id_pembayaran}",
            arguments = listOf(navArgument("id_pembayaran") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_mahasiswa = backStackEntry.arguments?.getString("id_pembayaran") ?: return@composable
            EntryPembayaranScreen(
                id_mahasiswa = id_mahasiswa,
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}
