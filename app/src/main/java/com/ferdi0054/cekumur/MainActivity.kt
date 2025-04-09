package com.ferdi0054.cekumur

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ferdi0054.cekumur.ui.screen.MainScreen
import com.ferdi0054.cekumur.ui.screen.Slide1
import com.ferdi0054.cekumur.ui.theme.CekUmurTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CekUmurTheme {
                var currentPage by remember { mutableStateOf("slide1") }

                when (currentPage) {
                    "slide1" -> Slide1(onLanjut = {
                        currentPage = "main"
                    })

                    "main" -> MainScreen()
                }
            }
        }
    }
}
