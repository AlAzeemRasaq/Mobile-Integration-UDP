package com.example.duoproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.*
import com.example.duoproject.ui.screens.HomeScreen
import com.example.duoproject.ui.screens.ThermalApp
import com.example.duoproject.ui.screens.RoomScreen
import com.example.duoproject.ui.theme.Theme

// Tag for logging to track lifecycle methods
private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    // onCreate is called when the activity is created
    @OptIn(ExperimentalMaterial3Api::class) // Opt-in annotation for using experimental APIs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enables edge-to-edge layout (fullscreen, no status bar)
        setContent { // Sets the content view using Jetpack Compose UI
            var isDarkTheme by remember { mutableStateOf(false) } // State to track the theme (dark/light)
            val navController = rememberNavController() // Navigation controller to handle screen transitions

            // Applying theme based on isDarkTheme
            Theme(darkTheme = isDarkTheme) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Thermal Checker") }, // TopAppBar with title
                            actions = {
                                IconButton(onClick = {
                                    isDarkTheme = !isDarkTheme // Toggle theme on click
                                }) {
                                    Icon(
                                        imageVector = if (isDarkTheme) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                                        contentDescription = "Toggle Theme" // Theme toggle icon (sun/moon)
                                    )
                                }
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->  // Padding to avoid overlap with the TopAppBar
                    NavHost(
                        navController = navController,  // Set up navigation host
                        startDestination = "home_screen",  // Define the start screen
                        modifier = Modifier.padding(innerPadding)  // Apply innerPadding for proper layout
                    ) {
                        // Define navigation routes
                        composable("home_screen") {
                            HomeScreen(navController = navController, isDarkTheme = isDarkTheme, onThemeToggle = { isDarkTheme = !isDarkTheme })
                        }
                        composable("room_screen") {
                            RoomScreen(navController = navController)
                        }
                    }
                    ThermalApp(modifier = Modifier.padding(innerPadding)) // Render the ThermalApp component
                }
            }
        }
    }

    // Lifecycle methods to log activity lifecycle states
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }
}

@Preview(showBackground = true, name = "Light Theme") // Preview the UI in light theme
@Composable
fun LightThemePreview() {
    Theme(darkTheme = false) {
        ThermalApp()
    }
}

@Preview(showBackground = true, name = "Dark Theme") // Preview the UI in dark theme
@Composable
fun DarkThemePreview() {
    Theme(darkTheme = true) {
        ThermalApp()
    }
}