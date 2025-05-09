package com.ferdi0054.cekumur.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ferdi0054.cekumur.R
import com.ferdi0054.cekumur.ui.theme.CekUmurTheme
import com.ferdi0054.cekumur.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

const val Key_ID_CATATAN = "idCatatan"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    LaunchedEffect(id) {
        if (id != null) {
            viewModel.loadDataById(id)
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(
                        text = if (id == null)
                            stringResource(id = R.string.app_name)
                        else
                            stringResource(id = R.string.edit_inputan)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        viewModel.namaError = viewModel.namaUser.isBlank()
                        viewModel.tanggalLahirError = viewModel.tanggalLahir.isBlank()
                        viewModel.tanggalPilihanError = viewModel.tanggalPilihan.isBlank()

                        if (viewModel.namaError || viewModel.tanggalLahirError || viewModel.tanggalPilihanError) {
                            Toast.makeText(context, "Semua data harus diisi!", Toast.LENGTH_SHORT)
                                .show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(
                                nama = viewModel.namaUser,
                                tglLahir = viewModel.tanggalLahir,
                                tglSkrg = viewModel.tanggalPilihan,
                                hasil = viewModel.hasilUmur
                            )
                        } else {
                            viewModel.update(
                                id = id,
                                nama = viewModel.namaUser,
                                tglLahir = viewModel.tanggalLahir,
                                tglSkrg = viewModel.tanggalPilihan,
                                hasil = viewModel.hasilUmur
                            )
                        }
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            viewModel.showDialog = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(modifier = Modifier.padding(innerPadding), viewModel = viewModel)
    }
    if (id != null && viewModel.showDialog) {
        DisplayAlertDialog(
            onDismissRequest = { viewModel.showDialog = false }
        ) {
            viewModel.showDialog = false
            viewModel.delete(id)
            navHostController.popBackStack()
        }
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier, viewModel: DetailViewModel) {


    var showDatePicker by remember { mutableStateOf(false) }
    var pilihanAtauTanggal by remember { mutableStateOf("") }

    val formatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val regexTanggal = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}")
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

        OutlinedTextField(
            value = viewModel.namaUser,
            onValueChange = {
                viewModel.namaUser = it
                viewModel.namaError = false
            },
            label = { Text(stringResource(R.string.nama)) },
            isError = viewModel.namaError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (viewModel.namaError) {
            Text(
                text = stringResource(R.string.warning_nama),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        OutlinedTextField(
            value = viewModel.tanggalLahir,
            onValueChange = {
                viewModel.tanggalLahir = it
                viewModel.tanggalLahirError = false
            },
            label = { Text(stringResource(R.string.tgl_lahir)) },
            isError = viewModel.tanggalLahirError,
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
        if (viewModel.tanggalLahirError) {
            Text(
                text = stringResource(R.string.warning_lahir),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        OutlinedTextField(
            value = viewModel.tanggalPilihan,
            onValueChange = {
                viewModel.tanggalPilihan = it
                viewModel.tanggalPilihanError = false
            },
            label = { Text(stringResource(R.string.tgl_piliha)) },
            isError = viewModel.tanggalPilihanError,
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
        if (viewModel.tanggalPilihanError) {
            Text(
                text = stringResource(R.string.warning_pilihan),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Button(
            onClick = {
                viewModel.namaError = viewModel.namaUser.isBlank()
                viewModel.tanggalLahirError =
                    viewModel.tanggalLahir.isBlank() || !regexTanggal.matches(viewModel.tanggalLahir.trim())
                viewModel.tanggalPilihanError =
                    viewModel.tanggalPilihan.isBlank() || !regexTanggal.matches(viewModel.tanggalPilihan.trim())

                if (viewModel.namaError || viewModel.tanggalLahirError || viewModel.tanggalPilihanError) {
                    viewModel.hasilUmur = ""
                    Toast.makeText(context, "Pastikan semua data valid", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val birthDate = try {
                    formatter.parse(viewModel.tanggalLahir.trim())
                } catch (e: Exception) {
                    null
                }

                val selectedDate = try {
                    formatter.parse(viewModel.tanggalPilihan.trim())
                } catch (e: Exception) {
                    null
                }

                if (birthDate == null || selectedDate == null) {
                    Toast.makeText(context, "Format tanggal tidak valid", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val calLahir = Calendar.getInstance().apply { time = birthDate }
                val calPilihan = Calendar.getInstance().apply { time = selectedDate }

                if (calLahir.after(calPilihan)) {
                    viewModel.hasilUmur = "! Tanggal lahir tidak boleh setelah tanggal pilihan."
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
                    viewModel.hasilUmur = if (locale == "en") {
                        "${viewModel.namaUser}: Your age is $tahun years $bulan months $hari days"
                    } else {
                        "${viewModel.namaUser}: Umur Anda $tahun tahun $bulan bulan $hari hari"
                    }
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.hitung))
        }

        if (viewModel.hasilUmur.isNotEmpty()) {
            Text(
                text = viewModel.hasilUmur,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

            Button(
                onClick = {
                    if (viewModel.namaError || viewModel.tanggalLahirError || viewModel.tanggalPilihanError) {
                        viewModel.hasilUmur = ""
                        Toast.makeText(context, "Pastikan semua data valid", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }

                    shareData(
                        context = context,
                        message = context.getString(
                            R.string.bagikan_template,
                            viewModel.namaUser,
                            viewModel.tanggalLahir,
                            viewModel.tanggalPilihan,
                            viewModel.hasilUmur
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
                            viewModel.tanggalLahir = dateString
                            viewModel.tanggalLahirError = false
                        } else {
                            viewModel.tanggalPilihan = dateString
                            viewModel.tanggalPilihanError = false
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
        type = "text/plain"
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
        MainScreen(rememberNavController())
    }
}
