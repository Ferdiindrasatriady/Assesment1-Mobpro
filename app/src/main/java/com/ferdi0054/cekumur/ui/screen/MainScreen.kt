package com.ferdi0054.cekumur.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ferdi0054.cekumur.R
import com.ferdi0054.cekumur.ui.theme.CekUmurTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary

                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {navHostController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(modifier = Modifier.padding(innerPadding))
    }
}

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

    val context = LocalContext.current

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
            label = { Text(stringResource(R.string.nama)) },
            isError = namaError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (namaError) {
            Text(
                text = stringResource(R.string.warning_nama),
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
            label = { Text(stringResource(R.string.tgl_lahir)) },
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
                text = stringResource(R.string.warning_lahir),
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
            label = { Text(stringResource(R.string.tgl_piliha)) },
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
                text = stringResource(R.string.warning_pilihan),
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
                    val calLahir = Calendar.getInstance().apply { time = birthDate }
                    val calPilihan = Calendar.getInstance().apply { time = selectedDate }

                    if (calLahir.after(calPilihan)) {
                        hasilUmur = "! Tanggal lahir tidak boleh setelah tanggal pilihan."
                    } else {
                        var tahun = calPilihan.get(Calendar.YEAR) - calLahir.get(Calendar.YEAR)
                        var bulan = calPilihan.get(Calendar.MONTH) - calLahir.get(Calendar.MONTH)
                        var hari =
                            calPilihan.get(Calendar.DAY_OF_MONTH) - calLahir.get(Calendar.DAY_OF_MONTH)
                        if (hari < 0) {
                            bulan--
                            hari += calLahir.getActualMaximum(Calendar.DAY_OF_MONTH)
                        }

                        if (bulan < 0) {
                            tahun--
                            bulan += 12
                        }


                        val locale = Locale.getDefault().language
                        hasilUmur = if (locale == "en") {
                            "$namaUser: Your age is $tahun years $bulan months $hari days"
                        } else {
                            "$namaUser: Umur Anda $tahun tahun $bulan bulan $hari hari"
                        }

                    }

//                } else {
//                    hasilUmur = "! Format tanggal tidak valid."
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.hitung))
        }

        if (hasilUmur.isNotEmpty()) {
            Text(
                text = hasilUmur,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(
                            R.string.bagikan_template,
                            namaUser, tanggalLahir, tanggalPilihan, hasilUmur
                        )
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
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
private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/paint"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    CekUmurTheme {
        MainScreen(navHostController = rememberNavController())
    }
}