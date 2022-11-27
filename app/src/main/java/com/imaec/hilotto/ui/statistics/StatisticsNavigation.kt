package com.imaec.hilotto.ui.statistics

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val statisticsNavigationRoute = "statisticsRoute"

fun NavController.navigateToStatistics(navOptions: NavOptions? = null) {
    this.navigate(statisticsNavigationRoute, navOptions)
}

fun NavGraphBuilder.statisticsScreen() {
    composable(route = statisticsNavigationRoute) {
        StatisticsRoute()
    }
}
