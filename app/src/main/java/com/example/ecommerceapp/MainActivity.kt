package com.example.ecommerceapp

import LoginScreen
import MainViewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme
import com.example.newsapp.data.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //the code below is when you need your app to go directly to home if token exist
            // else it will need you to login first
            if(RetrofitInstance.token.isEmpty()){
                Navigator(screen = SplashScreen())
            }
            else{
                Navigator(screen = HomeScreen(RetrofitInstance.token))
            }

        }
    }
}



