package com.dimhans.dimhansiddapp.model

data class AuthResponse(
    val status: String? = null,
    val authorized: Boolean? = null,
    val error: String? = null,
    val email: String? = null
)
