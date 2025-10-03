package com.dimhans.dimhansiddapp.model

data class ProfileRequest(
    val action: String = "getprofile", // The action must match the one in your Google Script
    val email: String
)
