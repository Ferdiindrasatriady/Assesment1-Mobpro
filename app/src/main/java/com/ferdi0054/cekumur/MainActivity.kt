package com.ferdi0054.cekumur

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ferdi0054.cekumur.ui.theme.CekUmurTheme
import java.text.SimpleDateFormat
import java.time.Period
import java.time.ZoneId
import java.util.*

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CekUmurTheme {
                MainScreen()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var namaUser by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var tanggalPilihan by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var pilihanAtauTanggal by remember { mutableStateOf("") }

    val formatter = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) }

    var hasilUmur by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = namaUser,
            onValueChange = { namaUser = it },
            label = { Text(text = stringResource(R.string.nama_user)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tanggalLahir,
            onValueChange = { tanggalLahir = it },
            label = { Text(text = stringResource(R.string.tanggal_lahir)) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Ikon Kalender",
                    modifier = Modifier.clickable {
                        pilihanAtauTanggal = "lahir"
                        showDatePicker = true
                    }
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = tanggalPilihan,
            onValueChange = { tanggalPilihan = it },
            label = { Text(text = stringResource(R.string.tanggal_pilihan)) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Ikon Kalender",
                    modifier = Modifier.clickable {
                        pilihanAtauTanggal = "pilihan"
                        showDatePicker = true
                    }
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val birthDate = formatter.parse(tanggalLahir)
                val selectedDate = formatter.parse(tanggalPilihan)

                if (birthDate != null && selectedDate != null) {
                    val birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    val selectedLocalDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

                    if (birthLocalDate.isAfter(selectedLocalDate)) {
                        hasilUmur = "Tanggal lahir tidak boleh setelah tanggal pilihan."
                    } else {
                        val period = Period.between(birthLocalDate, selectedLocalDate)
                        hasilUmur = "$namaUser: umur Anda ${period.years} tahun ${period.months} bulan ${period.days} hari"
                    }

                } else {
                    hasilUmur = "Format tanggal tidak valid."
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }

        if (hasilUmur.isNotEmpty()) {
            Text(
                text = hasilUmur,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

    if (showDatePicker) {
        DatePickerModalInput(
            onDateSelected = { selectedMillis ->
                selectedMillis?.let {
                    val dateString = formatter.format(Date(it))
                    if (pilihanAtauTanggal == "lahir") {
                        tanggalLahir = dateString
                    } else if (pilihanAtauTanggal == "pilihan") {
                        tanggalPilihan = dateString
                    }
                }
                showDatePicker = false
            },
            onDismiss = {
                showDatePicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    CekUmurTheme {
        MainScreen()
    }
}