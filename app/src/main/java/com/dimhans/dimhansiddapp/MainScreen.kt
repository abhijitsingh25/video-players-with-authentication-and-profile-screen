package com.dimhans.dimhansiddapp

import ProgressViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dimhans.dimhansiddapp.auth.AuthViewModel
import com.dimhans.dimhansiddapp.profile.ProfileScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: AuthViewModel,
    onCardClick: (String) -> Unit,
// MODIFIED: Accept the navigation lambda
    progressViewModel: ProgressViewModel,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val items = listOf(BottomNavItem.Home, BottomNavItem.Profile)

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
        topBar = { MyTopAppBar() }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                // MODIFIED: Call your original HomeScreen and pass the lambda down.
                // This preserves your existing navigation.
                HomeScreen(onCardClick = onCardClick)
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(viewModel = viewModel, progressViewModel = progressViewModel)
            }
        }
    }
}

