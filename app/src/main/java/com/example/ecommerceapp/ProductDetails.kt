package com.example.ecommerceapp

import Product
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.ecommerceapp.components.BottomNavigationBar
import com.example.ecommerceapp.data.model.DataXXX
import com.example.example.Products
import com.example.newsapp.data.network.RetrofitInstance.token

class ProductDetails(val products: Products) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var selectedTab by remember { mutableStateOf(0) }
        val tabs = listOf("Home", "Categories", "Favorites", "Profile")

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

            // The main content of the product details page
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.White, Color(0xFFEDE7F6)) // Light gradient background
                        )
                    )
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Product Title
                Text(
                    text = products.name.toString(),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF9E77E2), Color(0xFFEDE7F6) ) // Updated gradient for product name
                            ),
                            shape = RoundedCornerShape(16.dp) // Rounded corners for product name container
                        )
                        .padding(12.dp)
                )

                // Product Image Carousel
                LazyRow(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(products.images) {
                        Card(
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .size(250.dp)
                                .shadow(8.dp)
                                .clip(RoundedCornerShape(16.dp)) // Rounded corners for images
                        ) {
                            AsyncImage(
                                model = it,
                                contentDescription = null,
                                contentScale = ContentScale.Crop, // Fit image into container
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                // Product Price
                Text(
                    text = "${products.price} $",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black, // Green text for price
                    modifier = Modifier.padding(16.dp)
                )

                // Product Description
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = products.description.toString(),
                    fontSize = 18.sp,
                    color = Color.Black, // Black text for better readability
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFF9E77E2), Color(0xFFEDE7F6) ) // Updated gradient for description
                            ),
                            shape = RoundedCornerShape(16.dp) // Rounded corners for description container
                        )
                        .padding(12.dp)
                )
            }
        }
    }
}
