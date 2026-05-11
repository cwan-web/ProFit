package com.cw.ProFit.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel (
    val id: String,
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("blood_type")
    val bloodType: String? = null,
    val allergies: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null
)
