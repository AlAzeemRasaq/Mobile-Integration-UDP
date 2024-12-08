package com.example.duoproject.data

import com.example.duoproject.R
import com.example.duoproject.model.Room

class DataSource {
    fun loadRooms(): List<Room> {
        return listOf(
            Room(R.drawable.room1, R.string.room1_name, temperature = 25),
            Room(R.drawable.room2, R.string.room2_name, temperature = 22),
            Room(R.drawable.room3, R.string.room3_name, temperature = 28),
            Room(R.drawable.room4, R.string.room4_name, temperature = 22),
            Room(R.drawable.room5, R.string.room5_name, temperature = 22),
            Room(R.drawable.room6, R.string.room6_name, temperature = 24),
        )
    }
}