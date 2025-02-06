package com.example.oposiciones


import androidx.activity.compose.setContent

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.oposiciones.compose.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}