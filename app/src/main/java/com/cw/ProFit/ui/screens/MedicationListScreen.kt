package com.cw.ProFit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cw.ProFit.data.SupabaseProvider
import com.cw.ProFit.data.models.MedicationModel
import com.cw.ProFit.data.repository.MedicineRepository
import com.cw.ProFit.ui.theme.primaryColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicationListViewModel : ViewModel() {
    private val repository = MedicineRepository(SupabaseProvider.client)

    private val _medications = MutableStateFlow<List<MedicationModel>>(emptyList())
    val medications: StateFlow<List<MedicationModel>> = _medications.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchMedications()
    }

    fun fetchMedications() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val list = repository.getMedications()
                _medications.value = list
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationListScreen(onBack: () -> Unit, viewModel: MedicationListViewModel = viewModel()) {
    val medications by viewModel.medications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Refresh the list whenever this screen is displayed
    LaunchedEffect(Unit) {
        viewModel.fetchMedications()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Medications") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = primaryColor)
            } else if (medications.isEmpty()) {
                Text(
                    text = "No medications found",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(medications) { medication ->
                        MedicationCard(medication)
                    }
                }
            }
        }
    }
}

@Composable
fun MedicationCard(medication: MedicationModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display Image if available
            if (!medication.imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = medication.imageUrl,
                    contentDescription = medication.name,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = medication.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Category: ", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text(text = medication.category ?: "N/A", fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Dosage: ", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text(text = medication.defaultDosage ?: "N/A", fontSize = 14.sp)
                }
                if (!medication.instructions.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = medication.instructions,
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        maxLines = 2
                    )
                }
            }
        }
    }
}
