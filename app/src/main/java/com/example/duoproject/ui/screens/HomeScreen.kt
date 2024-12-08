package com.example.duoproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)  // Use experimental Material 3 API
@Composable
fun HomeScreen(
    navController: NavController, // Navigation controller to handle navigation
    isDarkTheme: Boolean, // State to track the theme
    onThemeToggle: () -> Unit // Function to toggle the theme
) {
    Scaffold(
        topBar = { // Scaffold top bar for UI header
            TopAppBar(
                title = { Text("Home Screen") },
                actions = {
                    // Theme toggle button
                    IconButton(onClick = onThemeToggle) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                            contentDescription = "Toggle Theme"
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding -> // Padding adjustment to avoid overlap with the top bar
        Column(
            modifier = Modifier
                .padding(innerPadding) // Apply the inner padding
                .padding(16.dp),
            verticalArrangement = Arrangement.Center // Center content vertically
        ) {
            Button(
                onClick = { navController.navigate("room_screen") }, // Navigate to the room screen on click
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go to Room Screen") // Button text
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)  // Use experimental Material 3 API
@Composable
fun RoomScreen(navController: NavController, isDarkTheme: Boolean, onThemeToggle: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Room Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onThemeToggle) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                            contentDescription = "Toggle Theme"
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Room Screen Content Goes Here") // Placeholder content
        }
    }
}