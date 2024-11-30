package com.example.ecommerceapp

import MainViewModel
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

class FavouriteData(var token: String, var lang: String = "en"): Screen {
    @Composable
    override fun Content() {
        val viewModel = MainViewModel()
        viewModel.getFavorites(token, lang)
        val favoritesResponse = viewModel.favoritesResponse.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val loading = viewModel.isLoadingHome.collectAsState()

        var selectedTab by remember { mutableStateOf(2) } // Set Favorites tab as selected by default
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
                            listOf(Color(0xFFEDE7F6), Color(0xFFFFFFFF)) // Same gradient as CartScreen and HomeScreen
                        )
                    )
                    .padding(paddingValues)
            ) {
                if (loading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 50.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Color(0xFF7E57C2) // Same color for consistency
                    )
                } else {
                    // Favourites section header
                    Text(
                        "Favourites", // Favourites title
                        fontSize = 28.sp,  // Increased font size
                        fontWeight = FontWeight.Bold,  // Bold text
                        color = Color(0xFF7E57C2),  // Same color as CartScreen
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp)  // Padding for better alignment
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(favoritesResponse.value?.data?.data ?: emptyList()) { favoriteItem ->
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
                                        model = favoriteItem.product?.image,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .height(150.dp)
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        favoriteItem.product?.name.toString(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(top = 8.dp),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        "Price: ${favoriteItem.product?.price} $",
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
