package com.example.moviestest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviestest.presentation.screen.movies.MoviesScreen


@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "movies",
        modifier = modifier
    ) {
        composable("movies") {
            MoviesScreen()
        }
    }
}