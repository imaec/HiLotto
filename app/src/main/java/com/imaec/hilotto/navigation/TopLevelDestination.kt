package com.imaec.hilotto.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Recommend
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.imaec.hilotto.R
import com.imaec.hilotto.ui.home.homeNavigationRoute
import com.imaec.hilotto.ui.statistics.statisticsNavigationRoute

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
    val route: String
) {
    HOME(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.home,
        titleTextId = R.string.home,
        route = homeNavigationRoute
    ),
    STATISTICS(
        selectedIcon = Icons.Filled.AutoGraph,
        unselectedIcon = Icons.Outlined.AutoGraph,
        iconTextId = R.string.statistics,
        titleTextId = R.string.statistics,
        route = statisticsNavigationRoute
    ),
    RECOMMEND(
        selectedIcon = Icons.Filled.Recommend,
        unselectedIcon = Icons.Outlined.Recommend,
        iconTextId = R.string.recommend,
        titleTextId = R.string.recommend,
        route = "recommend"
    ),
    MY(
        selectedIcon = Icons.Filled.People,
        unselectedIcon = Icons.Outlined.People,
        iconTextId = R.string.my,
        titleTextId = R.string.my,
        route = "my"
    ),
    SETTING(
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        iconTextId = R.string.setting,
        titleTextId = R.string.setting,
        route = "setting"
    )
}
