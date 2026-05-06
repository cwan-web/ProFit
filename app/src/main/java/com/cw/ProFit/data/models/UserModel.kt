package com.cw.ProFit.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val email:String = "",
    val password:String,
    val id: String? = null,

)