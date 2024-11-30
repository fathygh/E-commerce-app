import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerceapp.HomeScreen
import com.example.ecommerceapp.R
import com.example.example.LoginRequest
import androidx.compose.material3.TextFieldDefaults

class LoginScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = MainViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val emailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }
        val passwordVisible = remember { mutableStateOf(false) }
        val isLoading = remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()

        val emailError = remember { mutableStateOf(false) }
        val passwordError = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDE7F6)) // Match RegisterScreen background color
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {
                // Login Image
                Image(
                    painter = painterResource(id = R.drawable.login_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Welcome Text
                Text(
                    text = "Welcome Back!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7E57C2) // Darker purple to match RegisterScreen
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Login to your account",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Email Field with Error Handling
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("Email Address") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon", tint = Color(0xFF7E57C2)) },
                    isError = emailError.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = Color(0xFF7E57C2),
                        unfocusedBorderColor = Color(0xFF9575CD)
                    )
                )
                if (emailError.value) {
                    Text(
                        text = "Invalid email address",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Password Field with Visibility Toggle
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("Password") },
                    placeholder = { Text("Enter your password", color = Color.Gray) }, // Placeholder for guidance
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Lock Icon", tint = Color(0xFF7E57C2))
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(
                                imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Password Visibility",
                                tint = Color(0xFF7E57C2)
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = passwordError.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White,
                        focusedBorderColor = if (passwordError.value) Color.Red else Color(0xFF7E57C2),
                        unfocusedBorderColor = if (passwordError.value) Color.Red else Color(0xFF9575CD)
                    )
                )

                if (passwordError.value) {
                    Text(
                        text = "Password must be at least 6 characters",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp) // Increased padding for better readability
                    )
                }


                Spacer(modifier = Modifier.height(30.dp))

                // Login Button with Loading Indicator
                Button(
                    onClick = {
                        if (emailState.value.isEmpty()) {
                            emailError.value = true
                        } else {
                            emailError.value = false
                        }
                        if (passwordState.value.length < 6) {
                            passwordError.value = true
                        } else {
                            passwordError.value = false
                        }

                        if (!emailError.value && !passwordError.value) {
                            isLoading.value = true
                            viewModel.login(
                                LoginRequest(email = emailState.value, password = passwordState.value),
                                onSucess = {
                                    isLoading.value = false
                                    navigator.push(HomeScreen(it))
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .shadow(5.dp, RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)), // Match RegisterScreen button color
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isLoading.value
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text(text = "Login", fontSize = 20.sp, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Register Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Don't have an account?", color = Color.Gray)
                    TextButton(onClick = {
                        navigator.push(RegisterScreen())
                    }) {
                        Text(text = "Register", color = Color(0xFF7E57C2), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
