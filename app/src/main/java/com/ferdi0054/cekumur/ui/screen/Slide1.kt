package com.ferdi0054.cekumur.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferdi0054.cekumur.R

@Composable
fun Slide1(lanjut: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.bingung
            ),
            contentDescription = "Ilustrasi Cek Umur",
            modifier = Modifier.size(250.dp)
        )
        Text(
            text = "Apakah Anda siap untuk mengecek umur Anda?",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 24.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Button(
            onClick = lanjut,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 32.dp)
        ) {
            Text(text = "Mulai Sekarang")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun Slide1Preview() {
    Slide1(lanjut = {})
}

