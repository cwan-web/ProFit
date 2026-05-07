package com.cw.ProFit.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cw.ProFit.ui.screens.AddMedicationScreen
import com.cw.ProFit.ui.screens.authentication.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home", // Sets the medication list as the first screen
        modifier = modifier
    ) {
        // 1. Home Screen Route
        composable(route = "home") {
            HomeScreen(
                onNavigateToAddMedication = {
                    // Correct syntax: no '=' sign
                    navController.navigate("add_medication")
                },
                modifier = TODO("")
            )
        }

        // 2. Add Medication Screen Route
        composable(route = "add_medication") {
            AddMedicationScreen(
                onBack = {
                    // Correct syntax: uses curly braces for the action
                    navController.popBackStack()
                }
            )
        }
    }
}
