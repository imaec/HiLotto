package com.imaec.hilotto.ui.statistics

import White
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.imaec.components.component.ui.HlSlideTab
import com.imaec.hilotto.ui.statistics.continues.ContinuesScreen
import com.imaec.hilotto.ui.statistics.oddeven.OddEvenScreen
import com.imaec.hilotto.ui.statistics.pick.PickScreen
import com.imaec.hilotto.ui.statistics.sum.SumScreen
import com.imaec.hilotto.ui.statistics.win.WinScreen
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
internal fun StatisticsRoute(
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        StatisticsScreen(
            modifier = Modifier
                .fillMaxHeight()
                .padding(padding)
                .background(White)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun StatisticsScreen(modifier: Modifier) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(modifier = modifier) {
        HlSlideTab(
            tabList = listOf("합계", "출현/미출현", "연속번호", "홀/짝", "당첨"),
            hasDivider = false,
            selectedIndex = pagerState.currentPage,
            onClickTab = { index ->
                scope.launch {
                    pagerState.scrollToPage(index)
                }
            }
        )
        StatisticsPager(pagerState)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StatisticsPager(pagerState: PagerState) {
    HorizontalPager(count = 5, state = pagerState) { page ->
        when (page) {
            0 -> SumScreen()
            1 -> PickScreen()
            2 -> ContinuesScreen()
            3 -> OddEvenScreen()
            4 -> WinScreen()
        }
    }
}

@Preview
@Composable
private fun HomeRoutePreview() {
    StatisticsRoute()
}
