package com.dimhans.dimhansiddapp.model

data class ProfileResponse(
    val status: String,
    val data: UserData?, // Reuse your existing UserData model
    val error: String?

)
