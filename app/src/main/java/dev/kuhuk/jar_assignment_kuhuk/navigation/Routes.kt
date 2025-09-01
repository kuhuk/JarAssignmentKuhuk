package dev.kuhuk.jar_assignment_kuhuk.navigation

object Routes {
    fun onBoardingScreen() = object : Deeplink {
        override val path = "onBoarding"
        override val route: String = path
    }
}

interface Deeplink {
    val route: String
    val path: String
}