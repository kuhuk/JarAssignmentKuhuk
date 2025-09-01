package dev.kuhuk.jar_assignment_kuhuk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kuhuk.jar_assignment_kuhuk.ui.screens.onBoarding.OnBoardingScreen

@Composable
fun RootNavigationGraph() {
    val navController = rememberNavController()

    val onBoardingScreenRoute = Routes.onBoardingScreen().route
    val startDestination = onBoardingScreenRoute

    NavHost(navController = navController, startDestination = startDestination) {
        composable(onBoardingScreenRoute) {
            OnBoardingScreen()
        }
    }
}