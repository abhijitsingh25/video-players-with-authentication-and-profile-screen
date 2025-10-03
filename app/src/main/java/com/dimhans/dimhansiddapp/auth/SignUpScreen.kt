package com.dimhans.dimhansiddapp.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dimhans.dimhansiddapp.R
import com.dimhans.dimhansiddapp.model.UserData
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var childName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var motherName by rememberSaveable { mutableStateOf("") }
    var fatherName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black
        ),
        startY = 300f
    )

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().background(brush)){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 86.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.dimhans_logo_removebg),
                contentDescription = "logo"
            )

            Spacer(modifier =Modifier.padding(16.dp))
            Text("SignUp/ನೋಂದಣಿ", style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold)

            Spacer(modifier =Modifier.padding(8.dp))


            // Child Name Field
            OutlinedTextField(
                value = childName,
                onValueChange = { childName = it },
                label = { Text("Child Name",color = Color.White) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email",color = Color.White) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mother's Name Field
            OutlinedTextField(
                value = motherName,
                onValueChange = { motherName = it },
                label = { Text("Mother's Name",color = Color.White) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Father's Name Field
            OutlinedTextField(
                value = fatherName,
                onValueChange = { fatherName = it },
                label = { Text("Father's Name",color = Color.White) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password",color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Confirm Password Field
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password",color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Button
            Button(
                onClick = {
                    if (childName.isBlank() || email.isBlank() || motherName.isBlank() ||
                        fatherName.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (password.length < 8) {
                        Toast.makeText(context, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val userData = UserData(
                        childName = childName,
                        email = email,
                        motherName = motherName,
                        fatherName = fatherName
                    )

                    viewModel.viewModelScope.launch {
                        if (viewModel.register(userData, password)) {
                            onSignUpSuccess()
                        } else {
                            Toast.makeText(context, viewModel.errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.2f)),
                shape = MaterialTheme.shapes.medium

            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Sign Up",color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Login Navigation
            TextButton(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Already have an account? Login",
                    color = Color.White)
            }
        }
    }
}