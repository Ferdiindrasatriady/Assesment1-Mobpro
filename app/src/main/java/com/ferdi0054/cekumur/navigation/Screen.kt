package com.ferdi0054.cekumur.navigation

sealed class Screen (val route: String) {
    data object Home: Screen("Slide1")
}