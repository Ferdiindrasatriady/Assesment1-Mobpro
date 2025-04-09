package com.ferdi0054.cekumur.ui.screen

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ferdi0054.cekumur.R
import com.ferdi0054.cekumur.ui.theme.CekUmurTheme
import java.text.SimpleDateFormat
import java.time.Period
import java.time.ZoneId
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
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

    var namaError by remember { mutableStateOf(false) }
    var tanggalLahirError by remember { mutableStateOf(false) }
    var tanggalPilihanError by remember { mutableStateOf(false) }

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

        // Input Nama
        OutlinedTextField(
            value = namaUser,
            onValueChange = {
                namaUser = it
                namaError = false
            },
            label = { Text("Nama") },
            isError = namaError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (namaError) {
            Text(
                text = "Nama tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        // Input Tanggal Lahir
        OutlinedTextField(
            value = tanggalLahir,
            onValueChange = {
                tanggalLahir = it
                tanggalLahirError = false
            },
            label = { Text("Tanggal Lahir") },
            isError = tanggalLahirError,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        pilihanAtauTanggal = "lahir"
                        showDatePicker = true
                    }
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (tanggalLahirError) {
            Text(
                text = "Tanggal lahir tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        // Input Tanggal Pilihan
        OutlinedTextField(
            value = tanggalPilihan,
            onValueChange = {
                tanggalPilihan = it
                tanggalPilihanError = false
            },
            label = { Text("Tanggal Pilihan") },
            isError = tanggalPilihanError,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        pilihanAtauTanggal = "pilihan"
                        showDatePicker = true
                    }
                )
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (tanggalPilihanError) {
            Text(
                text = "Tanggal pilihan tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Button(
            onClick = {
                namaError = namaUser.isBlank()
                tanggalLahirError = tanggalLahir.isBlank()
                tanggalPilihanError = tanggalPilihan.isBlank()

                if (namaError || tanggalLahirError || tanggalPilihanError) {
                    hasilUmur = ""
                    return@Button
                }

                val birthDate = formatter.parse(tanggalLahir)
                val selectedDate = formatter.parse(tanggalPilihan)

                if (birthDate != null && selectedDate != null) {
                    val birthLocal =
                        birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    val selectedLocal =
                        selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

                    if (birthLocal.isAfter(selectedLocal)) {
                        hasilUmur = "! Tanggal lahir tidak boleh setelah tanggal pilihan."
                    } else {
                        val period = Period.between(birthLocal, selectedLocal)
                        hasilUmur =
                            "$namaUser: Umur Anda ${period.years} tahun ${period.months} bulan ${period.days} hari"
                    }
                } else {
                    hasilUmur = "! Format tanggal tidak valid."
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Hitung")
        }

        if (hasilUmur.isNotEmpty()) {
            Text(
                text = hasilUmur,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        if (showDatePicker) {
            DatePickerModalInput(
                onDateSelected = { selectedMillis ->
                    selectedMillis?.let {
                        val dateString = formatter.format(Date(it))
                        if (pilihanAtauTanggal == "lahir") {
                            tanggalLahir = dateString
                            tanggalLahirError = false
                        } else if (pilihanAtauTanggal == "pilihan") {
                            tanggalPilihan = dateString
                            tanggalPilihanError = false
                        }
                    }
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
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