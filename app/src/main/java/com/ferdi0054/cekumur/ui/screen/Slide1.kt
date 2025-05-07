package com.ferdi0054.cekumur.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ferdi0054.cekumur.R
import com.ferdi0054.cekumur.navigation.Screen

@Composable
fun Slide1(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.bingung),
            contentDescription = "Ilustrasi Cek Umur",
            modifier = Modifier.size(250.dp)
        )
        Text(
            text = stringResource(R.string.intro_s1),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 24.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
        Button(
            onClick = { navController.navigate(Screen.SlideList.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 32.dp)
        ) {
            Text(text = stringResource(R.string.btn_s1))
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Slide1Preview() {
    val navController = rememberNavController()
    Slide1(navController = navController)
}
