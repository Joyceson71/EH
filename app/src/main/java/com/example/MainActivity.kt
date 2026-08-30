package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ui.navigation.MainScreen
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.HackPathTheme
import com.example.ui.viewmodel.HackPathViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: HackPathViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HackPathTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundDark
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

