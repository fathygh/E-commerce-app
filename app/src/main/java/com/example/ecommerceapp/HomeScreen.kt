package com.example.ecommerceapp

import MainViewModel
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import com.example.example.CartRequest
import com.example.example.FavouriteRequest

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.ecommerceapp.components.BottomNavigationBar

class HomeScreen(var token: String) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = MainViewModel()
        viewModel.getHomeData(token)
        val homeData = viewModel.homeResponse.collectAsState()
        val navigator = LocalNavigator.current
        val context = LocalContext.current
        var searchQuery by remember { mutableStateOf("") }
        val searchResponse by viewModel.searchResponse.collectAsState()


        var selectedTab by remember { mutableStateOf(0) }


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
                            listOf(Color(0xFFEDE7F6), Color(0xFFFFFFFF))
                        )
                    )
                    .padding(paddingValues)
            ) {
                // Header Section
                Text(
                    text = "BuyZa",
                    fontSize = 35.sp,
                    color = Color(0xFF7E57C2),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
                // Search TextField

                // Banner Section
                if (homeData.value?.data?.banners.isNullOrEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(60.dp)
                            .padding(16.dp),
                        color = Color(0xFF7E57C2),
                        strokeWidth = 6.dp
                    )
                } else {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        items(homeData.value?.data?.banners ?: emptyList()) { banner ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .width(200.dp)
                                    .height(120.dp)
                                    .clickable { /* Handle banner click */ }
                                    .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                AsyncImage(
                                    model = banner.image,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .height(180.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                )
                            }
                        }
                    }

                    // Indicators for the banners
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val bannerCount = homeData.value?.data?.banners?.size ?: 0
                        for (i in 0 until bannerCount) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(if (i == 0) Color.Gray else Color.LightGray)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Products Section
                Text(
                    "Trending Products",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                // Search TextField
                Row ( modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp),
                    horizontalArrangement = Arrangement.Center){
                    OutlinedTextField(
                        modifier = Modifier.width(250.dp).height(55.dp),
                        value = searchQuery,
                        onValueChange = { query -> searchQuery = query },
                        label = { Text("Search products") },
                        leadingIcon = @androidx.compose.runtime.Composable {
                            Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color(0xFF7E57C2)) },


                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White,
                            focusedBorderColor = Color(0xFF7E57C2),
                            unfocusedBorderColor = Color(0xFF9575CD)
                        )
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    // Search Button
                    Button(
                        onClick = {
                            // Trigger API call when button is clicked
                            if (searchQuery.isNotEmpty()) {
                                viewModel.getSearchData(token, lang="en", searchQuery)
                            }
                        },
                        modifier = Modifier
                            .shadow(5.dp, RoundedCornerShape(16.dp)).padding(vertical = 5 .dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Search")
                    }

                }


                Spacer(modifier = Modifier.height(8.dp))
                if (searchResponse?.data?.data.isNullOrEmpty()) {
                    if (homeData.value?.data?.products.isNullOrEmpty()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(60.dp)
                                .padding(16.dp),
                            color = Color(0xFF7E57C2),
                            strokeWidth = 6.dp
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            items(homeData.value?.data?.products ?: emptyList()) { product ->
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .clickable {
                                            if (navigator != null) {
                                                navigator.push(ProductDetails(product))
                                            }
                                        }
                                ) {
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.White
                                        ),
                                        modifier = Modifier.fillMaxSize(),
                                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.padding(8.dp)
                                        ) {
                                            AsyncImage(
                                                model = product.image,
                                                contentDescription = null,
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                                    .height(150.dp)
                                                    .fillMaxWidth()
                                                    .clip(RoundedCornerShape(12.dp))
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                product.name.toString(),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.Black,
                                                modifier = Modifier.padding(top = 8.dp),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                "${product.price} $",
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Black,
                                                modifier = Modifier.padding(bottom = 8.dp)
                                            )
                                            CartIcon(
                                                isCart = product.inCart == true,
                                                onToggleCart = { newCartState ->
                                                    viewModel.addOrRemoveFromCart(
                                                        token, "en",
                                                        CartRequest(product.id!!),
                                                        onSucess = {
                                                            product.inCart = newCartState
                                                            Toast.makeText(
                                                                context,
                                                                if (newCartState) "$it added to cart" else "$it removed from cart",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }


                                    FavoriteIcon(
                                        isFavorite = product.inFavorites == true,
                                        onToggleFavorite = {
                                            viewModel.addOrRemoveFromFavorites(token, "en",
                                                FavouriteRequest(product.id!!),onSucess = {
                                                    Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()

                                                })
                                        },
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(8.dp)

                                    )
                                }
                            }
                        }
                    }
                }else{
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(searchResponse?.data?.data?: emptyList()) { product ->
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        if (navigator != null) {
                                            navigator.push(SearchProductDetails(product))
                                        }
                                    }
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    modifier = Modifier.fillMaxSize(),
                                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        AsyncImage(
                                            model = product.image,
                                            contentDescription = null,
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .height(150.dp)
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            product.name.toString(),
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.Black,
                                            modifier = Modifier.padding(top = 8.dp),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            "${product.price} $",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black,
                                            modifier = Modifier.padding(bottom = 8.dp)
                                        )
                                        CartIcon(
                                            isCart = product.in_cart == true,
                                            onToggleCart = { newCartState ->
                                                viewModel.addOrRemoveFromCart(
                                                    token, "en",
                                                    CartRequest(product.id!!),
                                                    onSucess = {
                                                        product.in_cart = newCartState
                                                        Toast.makeText(
                                                            context,
                                                            if (newCartState) "$it added to cart" else "$it removed from cart",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                )
                                            }
                                        )
                                    }
                                }


                                FavoriteIcon(
                                    isFavorite = product.in_favorites == true,
                                    onToggleFavorite = {
                                        viewModel.addOrRemoveFromFavorites(token, "en",
                                            FavouriteRequest(product.id!!),onSucess = {
                                                Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()

                                            })
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(8.dp)

                                )
                            }
                        }
                    }
                }

                }
            }
        }}


    @Composable

    fun FavoriteIcon(
        isFavorite: Boolean,
        onToggleFavorite: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        IconButton(onClick = onToggleFavorite) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }


    @Composable
    fun CartIcon(
        isCart: Boolean,
        onToggleCart: (Boolean) -> Unit
    ) {
        IconButton(onClick = { onToggleCart(!isCart) }) {
            Icon(
                imageVector = if (isCart) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                contentDescription = null,
                tint = Color(0xFF4CAF50)
            )
        }
    }

