package dev.kuhuk.jar_assignment_kuhuk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dagger.hilt.android.AndroidEntryPoint
import dev.kuhuk.jar_assignment_kuhuk.navigation.RootNavigationGraph
import dev.kuhuk.jar_assignment_kuhuk.ui.screens.splash.SplashScreen
import dev.kuhuk.jar_assignment_kuhuk.ui.theme.JarassignmentkuhukTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var showSplash by remember { mutableStateOf(true) }

            if (showSplash) {
                SplashScreen { showSplash = false }
            } else {
                JarassignmentkuhukTheme {
                    RootNavigationGraph()
                }
            }
        }
    }
}