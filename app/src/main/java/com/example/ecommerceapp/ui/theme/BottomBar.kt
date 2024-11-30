package com.example.ecommerceapp.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    screens: List<() -> Screen>,
    tabs: List<String> = listOf("Home", "Cart", "Favorites"),
    icons: List<ImageVector> = listOf(
        Icons.Filled.Home,
        Icons.Filled.ShoppingCart,
        Icons.Filled.Favorite
    ),
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.current
    NavigationBar(
        modifier = modifier
            .clip(RoundedCornerShape(35.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF7E57C2), Color(0xFF6200EE))
                ) // Gradient with two colors
            )
            .fillMaxWidth()
            .height(110.dp)
    ) {
        tabs.forEachIndexed { index, tab ->
            val selectedColor by animateColorAsState(
                if (selectedTab == index) Color(0xFF000000) else Color.Gray // White text when selected
            )
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = {
                    onTabSelected(index)
                    navigator?.push(screens[index]()) // Navigate to the corresponding screen
                },
                label = { Text(tab, color = selectedColor) },
                icon = { Icon(icons[index], contentDescription = tab, tint = selectedColor) } // Icon tint matches text color
            )
        }
    }
}

// No need for the private fun push anymore
