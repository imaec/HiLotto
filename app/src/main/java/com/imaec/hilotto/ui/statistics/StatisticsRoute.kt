package com.imaec.hilotto.ui.statistics

import White
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imaec.components.component.ui.HlSlideTab

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLifecycleComposeApi::class
)
@Composable
internal fun StatisticsRoute(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val tabState by viewModel.tabState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        StatisticsScreen(
            modifier = Modifier
                .fillMaxHeight()
                .padding(padding)
                .background(White),
            tabState = tabState,
            onClickTab = viewModel::onClickTab
        )
    }
}

@Composable
internal fun StatisticsScreen(
    modifier: Modifier,
    tabState: StatisticsTabState,
    onClickTab: (Int) -> Unit
) {

    HlSlideTab(
        tabList = listOf("합계", "출현/미출현", "연속번호", "홀/짝", "당첨"),
        hasDivider = false,
        selectedIndex = tabState.currentIndex,
        onClickTab = onClickTab
    )
//    Column(modifier) {
//
//        when (tabState.currentIndex) {
//            0 -> {
//                // SumContent
//            }
//            1 -> {
//                // PickContent
//            }
//            2 -> {
//                // ContinuesContent
//            }
//            3 -> {
//                // OddEvenContent
//            }
//            4 -> {
//                // WinContent
//            }
//        }
//    }
}

@Preview
@Composable
private fun HomeRoutePreview() {
    StatisticsRoute()
}
