package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.screen.DetailScreen
import com.example.compose.screen.HomeScreen
import com.example.compose.screen.LanguageScreen
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.viewmodel.FoodViewModel
import com.example.compose.viewmodel.FoodViewModelFactory
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        setContent {
            ComposeTheme {
                val navController = rememberNavController()

                val foodViewModel: FoodViewModel = viewModel(
                    factory = FoodViewModelFactory("Burgir")
                )

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(navController, foodViewModel)
                    }

                    composable(
                        route = "detail/{title}/{subtitle}/{desc}/{image}",
                        arguments = listOf(
                            navArgument("image") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("title") ?: ""
                        val subtitle = backStackEntry.arguments?.getString("subtitle") ?: ""
                        val desc = backStackEntry.arguments?.getString("desc") ?: ""
                        val image = backStackEntry.arguments?.getInt("image") ?: 0

                        DetailScreen(title, subtitle, desc, image)
                    }

                    composable("language") {
                        LanguageScreen()
                    }
                }
            }
        }
    }
}