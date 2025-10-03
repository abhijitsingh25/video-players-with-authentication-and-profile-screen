package com.dimhans.dimhansiddapp.model

data class AuthRequest(
    val action: String,
    val childName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val motherName: String? = null,
    val fatherName: String? = null
)
