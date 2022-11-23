package com.imaec.hilotto.ui.main.compose

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.imaec.hilotto.navigation.TopLevelDestination
import com.imaec.hilotto.ui.main.MainViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainNavGraph(viewModel: MainViewModel) {
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
                    navController.navigate(
                        when (it) {
                            TopLevelDestination.HOME -> "home"
                            TopLevelDestination.STATISTICS -> "statistics"
                            TopLevelDestination.RECOMMEND -> "recommend"
                            TopLevelDestination.MY -> "my"
                            TopLevelDestination.SETTING -> "setting"
                        }
                    )
                },
                currentDestination = null
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding).consumedWindowInsets(padding)
        ) {
            composable(route = "home") {
                Text(text = "home")
            }
            composable(route = "statistics") {
                Text(text = "statistics")
            }
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
//    HlNavHost(
//        navController = navController,
//        onBackClick = {},
//        modifier = Modifier.padding(padding).consumedWindowInsets(padding)
//    )
}

@Composable
private fun HlBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        tonalElevation = 0.dp
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
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
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
