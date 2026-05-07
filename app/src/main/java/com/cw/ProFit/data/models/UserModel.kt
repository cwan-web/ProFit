package com.cw.ProFit.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val email:String = "",
    val id: String? = null,
   // val password: String = ""


)

