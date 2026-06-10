package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.local.LanguagePreferences
import com.example.compose.local.MovieDatabase
import com.example.compose.local.ThemePreferences
import com.example.compose.network.RetrofitClient
import com.example.compose.repository.MovieRepository
import com.example.compose.screen.DetailScreen
import com.example.compose.screen.HomeScreen
import com.example.compose.screen.LanguageScreen
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.viewmodel.MovieViewModel
import com.example.compose.viewmodel.MovieViewModelFactory
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG && Timber.forest().isEmpty()) {
            Timber.plant(Timber.DebugTree())
        }

        val database = MovieDatabase.getDatabase(applicationContext)
        val repository = MovieRepository(RetrofitClient.apiService, database.movieDao())

        val prefs = LanguagePreferences(this)
        val savedLanguage = prefs.getLanguage()

        val locale = java.util.Locale.forLanguageTag(savedLanguage)
        java.util.Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)

        val themePrefs = ThemePreferences(this)

        setContent {
            val systemTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(themePrefs.isDarkTheme(systemTheme)) }

            ComposeTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                val tmdbApiKey = "7016571ea82b3d615f585012a3af1d01"

                val movieViewModel: MovieViewModel = viewModel(
                    factory = MovieViewModelFactory(repository, tmdbApiKey),
                )

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            viewModel = movieViewModel,
                            isDarkTheme = isDarkTheme,
                            onThemeToggle = {
                                isDarkTheme = !isDarkTheme
                                themePrefs.saveTheme(isDarkTheme)
                            }
                        )
                    }

                    composable(
                        route = "detail/{title}/{overview}/{posterPath}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType },
                            navArgument("overview") { type = NavType.StringType },
                            navArgument("posterPath") { type = NavType.StringType }
                        )
                    ) { backStackEntry: NavBackStackEntry ->
                        val title = backStackEntry.arguments?.getString("title") ?: ""
                        val overview = backStackEntry.arguments?.getString("overview") ?: ""
                        val posterPath = backStackEntry.arguments?.getString("posterPath") ?: ""

                        DetailScreen(title, overview, posterPath) {
                            navController.popBackStack()
                        }
                    }

                    composable("language") {
                        LanguageScreen {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}