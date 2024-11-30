package com.example.ecommerceapp

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay

class SplashScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = true) {
            delay(9000) // Delay for splash effect
            navigator.push(LoginScreen()) // Navigate to the main screen
        }

        val scale = remember { Animatable(0.5f) }

        // Trigger the scale animation on load
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1000, // 1 second animation
                    easing = FastOutSlowInEasing
                )
            )
        }

        // Splash screen content
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDE7F6)) // Light purple background color
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_2),
                    contentDescription = null,
                    modifier = Modifier
                        .size(195.dp)
                        .padding(16.dp)
                        .scale(scale.value) // Apply the scale animation
                        .clip(CircleShape) // Make the image rounded
                        .graphicsLayer {
                            // Make the image blend with the background by adding transparency
                            alpha = 0.6f
                        }
                )

                Text(
                    text = "BuyZA", // Splash screen app name
                    fontSize = 40.sp, // Adjust font size as needed
                    color = Color(0xFF7E57C2), // Lighter purple text color
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )

                Text(
                    text = "Your one-stop shop for everything!", // Splash screen subtitle
                    fontSize = 20.sp, // Adjust font size as needed
                    color = Color(0xFFAFA0C7), // Lighter purple text color
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}