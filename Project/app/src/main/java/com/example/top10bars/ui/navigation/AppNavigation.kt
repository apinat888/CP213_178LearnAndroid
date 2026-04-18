package com.example.top10bars.ui.navigation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.top10bars.data.local.FavoritesManager
import com.example.top10bars.data.repository.BarRepository
import com.example.top10bars.ui.screens.detail.DetailScreen
import com.example.top10bars.ui.screens.detail.DetailViewModel
import com.example.top10bars.ui.screens.favorites.FavoritesScreen
import com.example.top10bars.ui.screens.favorites.FavoritesViewModel
import com.example.top10bars.ui.screens.home.HomeScreen
import com.example.top10bars.ui.screens.home.HomeViewModel
import com.example.top10bars.ui.screens.profile.ProfileScreen
import com.example.top10bars.ui.theme.DarkSurface
import com.example.top10bars.ui.theme.PurpleAccent
import com.example.top10bars.ui.theme.TextSecondary
import com.example.top10bars.ui.theme.TextPrimary

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector?) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Favorites : Screen("favorites", "Favorites", Icons.Filled.Favorite)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)
    object Detail : Screen("detail/{barId}", "Detail", null) {
        fun createRoute(barId: String) = "detail/$barId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    // Dependencies
    val repository = remember { BarRepository() }
    val favoritesManager = remember { FavoritesManager(context) }

    var hasLocationPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasLocationPermission = isGranted
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val items = listOf(Screen.Home, Screen.Favorites, Screen.Profile)
            
            if (currentRoute in items.map { it.route }) {
                NavigationBar(
                    containerColor = DarkSurface,
                    contentColor = TextPrimary
                ) {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon!!, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = PurpleAccent,
                                selectedTextColor = PurpleAccent,
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary,
                                indicatorColor = DarkSurface
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                // simple viewmodel factory to pass dependencies
                val viewModel: HomeViewModel = viewModel(
                    factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            return HomeViewModel(repository, favoritesManager) as T
                        }
                    }
                )
                HomeScreen(viewModel, hasLocationPermission) { barId ->
                    navController.navigate(Screen.Detail.createRoute(barId))
                }
            }
            composable(Screen.Favorites.route) {
                val viewModel: FavoritesViewModel = viewModel(
                    factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            return FavoritesViewModel(repository, favoritesManager) as T
                        }
                    }
                )
                FavoritesScreen(viewModel, hasLocationPermission) { barId ->
                    navController.navigate(Screen.Detail.createRoute(barId))
                }
            }
            composable(Screen.Profile.route) {
                ProfileScreen(favoritesManager)
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val barId = backStackEntry.arguments?.getString("barId") ?: return@composable
                val viewModel: DetailViewModel = viewModel(
                    factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            return DetailViewModel(barId, repository, favoritesManager) as T
                        }
                    }
                )
                DetailScreen(viewModel) { navController.popBackStack() }
            }
        }
    }
}
