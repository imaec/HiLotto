package com.imaec.hilotto.ui.main.compose

import White
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imaec.hilotto.navigation.TopLevelDestination
import com.imaec.hilotto.ui.home.homeNavigationRoute
import com.imaec.hilotto.ui.home.homeScreen
import com.imaec.hilotto.ui.main.MainViewModel
import com.imaec.hilotto.ui.statistics.statisticsScreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainNavGraph(
    viewModel: MainViewModel
//    windowSizeClass: WindowSizeClass,
//    networkMonitor: NetworkMonitor,
//    appState: HlAppState = rememberNiaAppState(
//    networkMonitor = networkMonitor,
//    windowSizeClass = windowSizeClass
//    )
) {
//    val background: @Composable (@Composable () -> Unit) -> Unit =
//        when (appState.currentTopLevelDestination) {
//            TopLevelDestination.HOME -> { content ->
//                HiGradientBackground(content = content)
//            }
//            else -> { content -> HiBackground(content = content) }
//        }

    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            HlBottomBar(
                destinations = listOf(
                    TopLevelDestination.HOME,
                    TopLevelDestination.STATISTICS,
                    TopLevelDestination.RECOMMEND,
                    TopLevelDestination.MY,
                    TopLevelDestination.SETTING
                ),
                onNavigateToDestination = {
                    navController.navigate(it.route)
                },
                currentDestination = null
            )
        }
    ) { padding ->
        HlNavHost(
            modifier = Modifier
                .padding(padding)
                .consumedWindowInsets(padding),
            navController = navController,
            onBackClick = {}
        )
    }
}

@Composable
fun HlNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen()
        statisticsScreen()
        composable(route = "recommend") {
            Text(text = "recommend")
        }
        composable(route = "my") {
            Text(text = "my")
        }
        composable(route = "setting") {
            Text(text = "setting")
        }
    }
}

@Composable
private fun HlBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        containerColor = White
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            HlNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}

@Composable
fun RowScope.HlNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun MainNavGraphPreview() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            HlBottomBar(
                destinations = listOf(
                    TopLevelDestination.HOME,
                    TopLevelDestination.STATISTICS,
                    TopLevelDestination.RECOMMEND,
                    TopLevelDestination.MY,
                    TopLevelDestination.SETTING
                ),
                onNavigateToDestination = {},
                currentDestination = null
            )
        }
    ) { padding ->
        HlNavHost(
            modifier = Modifier
                .padding(padding)
                .consumedWindowInsets(padding),
            navController = navController,
            onBackClick = {}
        )
    }
}
