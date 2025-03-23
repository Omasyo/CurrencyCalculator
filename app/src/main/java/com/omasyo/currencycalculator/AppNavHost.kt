package com.omasyo.currencycalculator

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omasyo.currencycalculator.ui.convert.ConvertCurrencyRoute
import kotlinx.serialization.Serializable

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController,
        startDestination = CurrencyConverter,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Up,
                spring(stiffness = Spring.StiffnessMediumLow)
            )
        },
        exitTransition =
        { fadeOut() + scaleOut(targetScale = 0.9f) },
        popEnterTransition =
        { fadeIn() + scaleIn(initialScale = 0.9f) },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Down,
                spring(stiffness = Spring.StiffnessMediumLow)
            )
        },
    ) {
        composable<CurrencyConverter> {
            ConvertCurrencyRoute(
                onHistoryTap = {},
            )
        }
        composable<History> {

        }
    }
}

@Serializable
data object CurrencyConverter

@Serializable
data object History