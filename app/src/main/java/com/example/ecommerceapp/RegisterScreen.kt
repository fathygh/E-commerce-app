import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import com.example.example.RegisterRequest
import androidx.compose.material3.TextFieldDefaults

class RegisterScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = MainViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val fullNameState = remember { mutableStateOf("") }
        val emailState = remember { mutableStateOf("") }
        val passwordState = remember { mutableStateOf("") }
        val phoneState = remember { mutableStateOf("") }
        val passwordVisible = remember { mutableStateOf(false) }
        val isLoading = remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()

        // Error states for each field
        val fullNameError = remember { mutableStateOf(false) }
        val emailError = remember { mutableStateOf(false) }
        val passwordError = remember { mutableStateOf(false) }
        val phoneError = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDE7F6))
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
                // Image at the top
                Image(
                    painter = painterResource(id = R.drawable.login_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.height(15.dp))

                // Title
                Text(
                    text = "Create Account",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF7E57C2)
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Full Name field with error handling
                OutlinedTextField(
                    value = fullNameState.value,
                    onValueChange = { fullNameState.value = it },
                    label = { Text("Full Name") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Person Icon", tint = Color(0xFF7E57C2))
                    },
                    isError = fullNameError.value,
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
                if (fullNameError.value) {
                    Text(
                        text = "Full Name is required",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // Email field with error handling
                OutlinedTextField(
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text("Email Address") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email Icon", tint = Color(0xFF7E57C2))
                    },
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

                // Password field with visibility toggle and error handling
                OutlinedTextField(
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Lock Icon", tint = Color(0xFF7E57C2))
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(
                                imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible.value) "Hide password" else "Show password",
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
                        focusedBorderColor = Color(0xFF7E57C2),
                        unfocusedBorderColor = Color(0xFF9575CD)
                    )
                )
                if (passwordError.value) {
                    Text(
                        text = "Password must be at least 6 characters",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }

                // Phone number field with error handling
                OutlinedTextField(
                    value = phoneState.value,
                    onValueChange = { phoneState.value = it },
                    label = { Text("Phone Number") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = "Phone Icon", tint = Color(0xFF7E57C2))
                    },
                    isError = phoneError.value,
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
                if (phoneError.value) {
                    Text(
                        text = "Phone number is required",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Register Button with validation
                Button(
                    onClick = {
                        // Reset error states
                        fullNameError.value = fullNameState.value.isEmpty()
                        emailError.value = emailState.value.isEmpty()
                        passwordError.value = passwordState.value.length < 6
                        phoneError.value = phoneState.value.isEmpty()

                        // Proceed with registration only if no errors
                        if (!fullNameError.value && !emailError.value && !passwordError.value && !phoneError.value) {
                            isLoading.value = true
                            viewModel.register(
                                RegisterRequest(
                                    email = emailState.value,
                                    password = passwordState.value,
                                    name = fullNameState.value,
                                    phone = phoneState.value
                                ),
                                onSucess = {
                                    navigator.push(HomeScreen(it))
                                    viewModel.getHomeData("Bearer ${viewModel.registerResponse.value?.data?.token}")
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .shadow(5.dp, RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E57C2)),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isLoading.value
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text(text = "Register", fontSize = 20.sp, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Already have an account? Login text
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Already have an account?", color = Color.Gray)
                    TextButton(onClick = {
                        navigator.push(LoginScreen())
                    }) {
                        Text(text = "Login", color = Color(0xFF7E57C2), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
