package com.example.duoproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.duoproject.model.Room
import com.example.duoproject.data.DataSource

private const val TAG = "RoomScreen"

@ExperimentalMaterial3Api
@Composable
fun RoomScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Room Screen") },
                actions = {
                    IconButton(onClick = { navController.navigate("home_screen") }) {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = "Go to Home") // Icon for home navigation
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Back button functionality
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding -> // Avoid UI elements being covered by the top bar
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ThermalApp() // Render the thermal app component
        }
    }
}

@Composable
fun ThermalApp(modifier: Modifier = Modifier) {
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RoomList(
            roomList = DataSource().loadRooms(), // Load the list of rooms from the data source
        )
    }
}

@Composable
fun RoomList(roomList: List<Room>, modifier: Modifier = Modifier) {
    // Use a mutable state list to track the rooms' data
    val roomStates = remember { roomList.map { it.copy(temperature = it.temperature) }.toMutableStateList() }

    // Render a lazy column of room items
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(roomList.size) { index -> // Iterate over each room item
            RoomCard(
                room = roomStates[index],
                onTemperatureChange = { newTemp -> // Handle temperature change for a room
                    roomStates[index] = roomStates[index].copy(temperature = newTemp)
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun RoomCard(
    room: Room,
    onTemperatureChange: (Int) -> Unit, // Function to change the temperature
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) } // Track if the card is expanded
    var showTemperature by remember { mutableStateOf(false) } // Track whether to show temperature

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize( // Smooth animation when content changes
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        onClick = { isExpanded = !isExpanded } // Toggle expanded state on click
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(room.imageResourceId), // Display room image
                contentDescription = stringResource(room.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = LocalContext.current.getString(room.stringResourceId),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
            if (isExpanded) { // Expand the card content
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { showTemperature = !showTemperature }) {
                    Text(text = if (showTemperature) "Hide Temperature" else "Show Temperature")
                }
                if (showTemperature) { // Show temperature controls
                    Spacer(modifier = Modifier.height(8.dp))
                    Column {
                        Text(
                            text = "Temperature: ${room.temperature}°C",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = { onTemperatureChange(room.temperature - 1) }) {
                                Text("-")
                            }
                            Button(onClick = { onTemperatureChange(room.temperature + 1) }) {
                                Text("+")
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Slider(
                            value = room.temperature.toFloat(),
                            valueRange = 0f..40f,
                            onValueChange = { onTemperatureChange(it.toInt()) } // Adjust temperature using the slider
                        )
                    }
                }
            }
        }
    }
}
