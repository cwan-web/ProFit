package com.cw.ProFit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cw.ProFit.ui.screens.AddMedicationScreen
import com.cw.ProFit.ui.screens.MedicationListScreen
import com.cw.ProFit.ui.screens.SplashScreen
import com.cw.ProFit.ui.screens.authentication.home.HomeScreen
import com.cw.ProFit.ui.screens.authentication.login.LoginScreen
import com.cw.ProFit.ui.screens.authentication.forgotpassword.ForgotPasswordScreen
import com.cw.ProFit.ui.screens.authentication.register.RegisterScreen
import com.cw.ProFit.ui.screens.Profile.ProfileScreen
import com.cw.ProFit.ui.screens.settings.SettingsScreen


@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = "splash", 
        modifier = modifier
    ) {
        // 1. Splash Screen Route
        composable(route = "splash") {
            SplashScreen(
                onInitializationComplete = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                checkSupabaseSession = {}
            )
        }
        
        // 2. Login
        composable(route = "login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = { 
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToForgotPassword = { navController.navigate("forgotpassword") },
                modifier = Modifier,
            )
        }

        // 3. Register Screen Route
        composable(route = "register") {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        // 4. Home Screen Route
        composable(route = "home") {
            HomeScreen(navController = navController)
        }

        composable(route = "add_medication") {
            AddMedicationScreen(onBack = { navController.popBackStack() })
        }

        composable(route = "medication_list") {
            MedicationListScreen(onBack = { navController.popBackStack() })
        }

        composable(route = "forgotpassword") {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("forgotpassword") { inclusive = true }
                    }
                }
            )
        }

        composable(route = "profile") {
            ProfileScreen(navController = navController)
        }

        composable(route = "settings") {
            SettingsScreen(navController = navController)
        }
    }
}
