package com.dimhans.dimhansiddapp

import com.dimhans.dimhansiddapp.model.AuthRequest
import com.dimhans.dimhansiddapp.model.AuthResponse
import com.dimhans.dimhansiddapp.model.ProfileRequest
import com.dimhans.dimhansiddapp.model.ProfileResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("exec")
    suspend fun authRequest(@Body request: AuthRequest): Response<AuthResponse>

    @POST("exec")
    suspend fun getProfile(@Body request: ProfileRequest): Response<ProfileResponse>


    companion object {
        private const val BASE_URL = "LINK TO YOUR GOOGLE SHEET"
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
