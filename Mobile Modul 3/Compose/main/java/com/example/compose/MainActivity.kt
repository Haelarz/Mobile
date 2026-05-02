package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.screen.DetailScreen
import com.example.compose.screen.HomeScreen
import com.example.compose.screen.LanguageScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {

                composable("home") {
                    HomeScreen(navController)
                }

                composable(
                    "detail/{title}/{subtitle}/{desc}/{image}"
                ) { backStackEntry ->

                    val title =
                        backStackEntry.arguments?.getString("title") ?: ""

                    val subtitle =
                        backStackEntry.arguments?.getString("subtitle") ?: ""

                    val desc =
                        backStackEntry.arguments?.getString("desc") ?: ""

                    val image =
                        backStackEntry.arguments?.getString("image")?.toInt() ?: 0

                    DetailScreen(
                        title,
                        subtitle,
                        desc,
                        image
                    )
                }
                composable("language") {
                    LanguageScreen()
                }
            }
        }
    }
}