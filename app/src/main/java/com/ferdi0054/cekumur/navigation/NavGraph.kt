package com.ferdi0054.cekumur.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ferdi0054.cekumur.ui.screen.Key_ID_CATATAN

import com.ferdi0054.cekumur.ui.screen.MainScreen
import com.ferdi0054.cekumur.ui.screen.Slide1
import com.ferdi0054.cekumur.ui.screen.SlideList


@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            Slide1(navController = navController)
        }
        composable(route = Screen.SlideList.route){
            SlideList(navController)
        }
        composable(route = Screen.CekUmur.route) {
            MainScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(Key_ID_CATATAN) {type = NavType.LongType}
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(Key_ID_CATATAN)
            MainScreen(navController,id)
        }
    }
}