package com.ferdi0054.cekumur.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
    }
}