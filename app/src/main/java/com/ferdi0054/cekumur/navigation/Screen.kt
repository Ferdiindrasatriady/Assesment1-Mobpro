package com.ferdi0054.cekumur.navigation

import com.ferdi0054.cekumur.ui.screen.Key_ID_CATATAN

sealed class Screen(val route: String) {
    data object Home : Screen("Slide1")
    data object SlideList : Screen("SlideList")
    data object CekUmur : Screen("MainScreen")
    data object FormUbah : Screen("MainScreen/{$Key_ID_CATATAN}") {
        fun withId(id: Long) = "MainScreen/$id"
    }
}