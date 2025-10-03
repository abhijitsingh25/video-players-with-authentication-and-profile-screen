package com.dimhans.dimhansiddapp.auth

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dimhans.dimhansiddapp.ApiService
import com.dimhans.dimhansiddapp.PreferencesManager
import com.dimhans.dimhansiddapp.model.AuthRequest
import com.dimhans.dimhansiddapp.model.ProfileRequest
import com.dimhans.dimhansiddapp.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = ApiService.create()
    private val prefs = PreferencesManager(application)

    private val _currentUser = MutableStateFlow<UserData?>(null)
    val currentUser: StateFlow<UserData?> = _currentUser.asStateFlow()

    // --- ADDED: State to track if the initial authentication check is complete ---
    private val _isAuthCheckComplete = MutableStateFlow(false)
    val isAuthCheckComplete: StateFlow<Boolean> = _isAuthCheckComplete.asStateFlow()

    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    init {
        viewModelScope.launch {
            // Auto-login if credentials exist
            prefs.savedEmailFlow.first()?.let { email ->
                prefs.savedPasswordFlow.first()?.let { password ->
                    login(email, password)
                }
            }
            // --- ADDED: Mark the check as complete regardless of outcome ---
            _isAuthCheckComplete.value = true
        }
    }

    suspend fun register(userData: UserData, password: String): Boolean {
        // ... (No changes in this function)
        return try {
            isLoading = true
            val response = apiService.authRequest(
                AuthRequest(
                    action = "register",
                    childName = userData.childName,
                    email = userData.email,
                    password = password,
                    motherName = userData.motherName,
                    fatherName = userData.fatherName
                )
            )
            if (response.isSuccessful && response.body()?.status == "registered") {
                _currentUser.value = userData
                prefs.saveCredentials(userData.email, password)
                true
            } else {
                errorMessage = response.body()?.error ?: "Registration failed"
                false
            }
        } catch (e: Exception) {
            errorMessage = "Network error: ${e.message}"
            false
        } finally {
            isLoading = false
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        return try {
            isLoading = true
            errorMessage = "" // Clear previous errors
            val response = apiService.authRequest(
                AuthRequest(action = "login", email = email, password = password)
            )
            if (response.isSuccessful && response.body()?.authorized == true) {
                // Fetch full profile details after a successful login
                fetchProfile(email)
                prefs.saveCredentials(email, password)
                true
            } else {
                errorMessage = response.body()?.error ?: "Invalid credentials"
                _currentUser.value = null // Ensure user is logged out on failure
                false
            }
        } catch (e: Exception) {
            errorMessage = "Network error: ${e.message}"
            _currentUser.value = null
            false
        } finally {
            isLoading = false
        }
    }

    suspend fun fetchProfile(email: String) {
        try {
            errorMessage = "" // Clear previous errors
            val profileResponse = apiService.getProfile(ProfileRequest(action = "getProfile", email = email))
            if (profileResponse.isSuccessful && profileResponse.body()?.status == "success") {
                _currentUser.value = profileResponse.body()?.data
            } else {
                errorMessage = profileResponse.body()?.error ?: "Could not fetch profile."
            }
        } catch (e: Exception) {
            errorMessage = "Network error while fetching profile: ${e.message}"
        }
    }

    fun logout() {
        viewModelScope.launch {
            prefs.clearCredentials()
            _currentUser.value = null
        }
    }
}

