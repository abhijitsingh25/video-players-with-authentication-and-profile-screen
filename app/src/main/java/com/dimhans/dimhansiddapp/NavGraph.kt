package com.dimhans.dimhansiddapp

import ProgressViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dimhans.dimhansiddapp.auth.AuthViewModel
import com.dimhans.dimhansiddapp.auth.LoginScreen
import com.dimhans.dimhansiddapp.auth.SignUpScreen
import com.dimhans.dimhansiddapp.helpscreens.BrushTeethScreen
import com.dimhans.dimhansiddapp.helpscreens.EatingHealthyScreen
import com.dimhans.dimhansiddapp.helpscreens.ReadyForSchoolScreen
import com.dimhans.dimhansiddapp.helpscreens.WashHandsScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Main : Screen("main")

    // Your other screens...
    object BrushTeeth : Screen("brush_teeth")
    object WashHands : Screen("wash_hands")
    object ReadyForSchool : Screen("ready_for_school")
    object EatingHealthy : Screen("eating_healthy")
    object Toothpaste : Screen("toothpaste")
    object FrontTeeth : Screen("front_teeth")
    object BackTeeth : Screen("back_teeth")
}

@Composable
fun AppNavigation(progressViewModel: ProgressViewModel) {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = viewModel()
    val currentUser by viewModel.currentUser.collectAsState()
    val isAuthCheckComplete by viewModel.isAuthCheckComplete.collectAsState()

    // Show a loading screen until the initial authentication check is complete.
    // This prevents a white screen on rotation or startup.
    if (!isAuthCheckComplete) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Once the check is complete, build the navigation graph.
        NavHost(
            navController = navController,
            // The start destination is now determined by the result of the auth check.
            startDestination = if (currentUser != null) Screen.Main.route else Screen.Login.route
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) }
                )
            }

            composable(Screen.SignUp.route) {
                SignUpScreen(
                    onSignUpSuccess = {
                        navController.navigate(Screen.Main.route) {
                            popUpTo(Screen.SignUp.route) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { navController.popBackStack() }
                )
            }

            composable(Screen.Main.route) {
                MainScreen(
                    viewModel = viewModel,
                    onCardClick = { route -> navController.navigate(route) },
                    progressViewModel = progressViewModel,
                    onLogout = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Main.route) { inclusive = true }
                        }
                    }
                )
            }

            // Your other composable routes for the help screens remain the same.
            composable(Screen.BrushTeeth.route) {
                BrushTeethScreen(onSubTaskClick = { route ->
                    navController.navigate(route)
                })
            }
            composable(Screen.WashHands.route) { WashHandsScreen() }
            composable(Screen.ReadyForSchool.route) { ReadyForSchoolScreen() }
            composable(Screen.EatingHealthy.route) { EatingHealthyScreen() }
            composable(Screen.Toothpaste.route) { ToothpasteScreen(progressViewModel) }
            composable(Screen.FrontTeeth.route) { FrontTeethScreen() }
            composable(Screen.BackTeeth.route) { BackTeethScreen() }
        }
    }
}

