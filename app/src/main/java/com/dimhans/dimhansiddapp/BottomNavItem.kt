package com.dimhans.dimhansiddapp


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * A sealed class to define the screens for the bottom navigation bar.
 * Using a sealed class ensures type safety and makes it easy to manage
 * all navigation items in one place.
 */
sealed class BottomNavItem(
    val route: String,      // The navigation route string for the screen
    val icon: ImageVector,  // The icon to display in the navigation bar
    val label: String       // The text label for the navigation item
) {
    // Represents the Home screen in the navigation bar
    object Home : BottomNavItem(
        route = "home",
        icon = Icons.Default.Home,
        label = "Home"
    )

    // Represents the Profile screen in the navigation bar
    object Profile : BottomNavItem(
        route = "profile",
        icon = Icons.Default.Person,
        label = "Profile"
    )
}
