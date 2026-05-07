package com.cw.ProFit.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MedicationModel(
    val id : Long? = null,
    val name : String,
    val category: String? = null,
    @SerialName("default_dosage")
    val defaultDosage: String? = null,
    val instructions: String? = null
)