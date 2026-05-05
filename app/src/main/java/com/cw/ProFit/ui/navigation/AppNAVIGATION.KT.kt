package com.cw.ProFit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppNavigation(navController: NavHostControllerController, modifier: Modifier){
    androidx.navigation.NavHost(
        navController = navController,
        startDestination = ROUTES.onboarding.name

    )



}
