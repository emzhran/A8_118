package com.example.mohammadzachranzachary118.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mohammadzachranzachary118.ui.view.bangunan.DestinasiEntry
import com.example.mohammadzachranzachary118.ui.view.bangunan.DestinasiHome
import com.example.mohammadzachranzachary118.ui.view.bangunan.DestinasiUpdate
import com.example.mohammadzachranzachary118.ui.view.bangunan.EntryBangunanScreen
import com.example.mohammadzachranzachary118.ui.view.bangunan.HomeBangunanScreen
import com.example.mohammadzachranzachary118.ui.view.bangunan.UpdateBangunanScreen
import com.example.mohammadzachranzachary118.ui.view.screen.MainMenuScreen
import com.example.mohammadzachranzachary118.ui.view.screen.MainScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = MainScreen.route,
        modifier = Modifier
    ) {
        composable(MainScreen.route) {
            MainMenuScreen(
                onNavigateToBangunan = { navController.navigate(DestinasiHome.route) }
            )
        }
        composable(DestinasiHome.route) {
            HomeBangunanScreen(
                navigateBack = { navController.navigateUp() },
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToUpdate = { bangunan ->
                    navController.navigate("${DestinasiUpdate.route}/${bangunan.idbangunan}")
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
    }
}
