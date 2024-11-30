package com.example.ecommerceapp

import MainViewModel
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.ecommerceapp.components.BottomNavigationBar
import com.example.example.FavouriteRequest

class CartScreen(var token: String, var lang: String = "en"): Screen {
    @Composable
    override fun Content() {
        val viewModel = MainViewModel()
        viewModel.getCarts(token, lang)
        val cartResponse = viewModel.cartResponse.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        var selectedTab by remember { mutableStateOf(1) } // Set Cart tab as selected by default

        val screens = listOf(
            { HomeScreen(token) },
            { CartScreen(token) },
            { FavouriteData(token) }
        )

        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    selectedTab = selectedTab,
                    onTabSelected = { index ->
                        selectedTab = index
                    },
                    screens = screens // Pass the list of screens
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFEDE7F6), Color(0xFFFFFFFF)) // Same gradient as HomeScreen
                        )
                    )
                    .padding(paddingValues)
            ) {
                // Emphasize the Cart header text
                Text(
                    "Carts", // Cart title
                    fontSize = 28.sp,  // Increased font size
                    fontWeight = FontWeight.Bold,  // Bold text
                    color = Color(0xFF7E57C2),  // Same color as the Home screen title
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)  // Padding for better alignment
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display a progress indicator or the cart items
                if (cartResponse.value?.data?.cart_items.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF7E57C2),  // Same color as the HomeScreen progress indicator
                            strokeWidth = 4.dp
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(cartResponse.value?.data?.cart_items ?: emptyList()) { cartItem ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .shadow(6.dp, RoundedCornerShape(12.dp))
                                    .clip(RoundedCornerShape(12.dp)),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                )
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    AsyncImage(
                                        model = cartItem.product?.image,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(150.dp)
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        cartItem.product?.name.toString(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(top = 8.dp),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "Price: ${cartItem.product?.price} $",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}