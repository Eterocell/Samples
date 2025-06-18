package com.eterocell.samples.permission

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eterocell.samples.permission.screen.CameraCaptureScreen
import com.eterocell.samples.permission.screen.CameraPermissionScreen
import com.eterocell.samples.permission.screen.LocationPermissionScreen

@Composable
fun PermissionHost() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Scaffold { innerPadding ->
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val hideNavBar = currentRoute == PermissionScreens.CameraCapture.route

            CompositionLocalProvider(LocalNavController provides navController) {
                NavigationSuiteScaffold(
                    navigationSuiteItems = {
                        if (!hideNavBar) {
                            PermissionScreens.entries.forEach { screen ->
                                if (screen.inNavBar) {
                                    item(
                                        icon = {
                                            Icon(
                                                screen.icon,
                                                contentDescription = screen.route,
                                            )
                                        },
                                        label = { Text(screen.route) },
                                        selected = currentRoute == screen.route,
                                        onClick = {
                                            if (currentRoute != screen.route) {
                                                navController.navigate(screen.route)
                                            }
                                        },
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier.padding(innerPadding),
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = PermissionScreens.Location.route,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable(PermissionScreens.Location.route) {
                            LocationPermissionScreen()
                        }
                        composable(PermissionScreens.Camera.route) {
                            CameraPermissionScreen()
                        }
                        composable(PermissionScreens.CameraCapture.route) {
                            CameraCaptureScreen()
                        }
                    }
                }
            }
        }
    }
}

enum class PermissionScreens(
    val route: String,
    val icon: ImageVector,
    val inNavBar: Boolean,
) {
    Location("Location", Icons.Filled.LocationOn, true),
    Camera("Camera", Icons.Filled.CameraAlt, true),
    CameraCapture("CameraCapture", Icons.Filled.Error, false),
}

val LocalNavController =
    staticCompositionLocalOf<NavHostController> {
        error("NavController not provided")
    }
