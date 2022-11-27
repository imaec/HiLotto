package com.imaec.hilotto.ui.statistics

import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor() : BaseViewModel() {

    private val _tabState = MutableStateFlow(
        StatisticsTabState(
            titles = listOf(
                R.string.sum,
                R.string.pick,
                R.string.continues,
                R.string.odd_even,
                R.string.win
            ),
            currentIndex = 0
        )
    )
    val tabState: StateFlow<StatisticsTabState> = _tabState.asStateFlow()

    fun onClickTab(index: Int) {
        if (index != tabState.value.currentIndex) {
            _tabState.update {
                it.copy(currentIndex = index)
            }
        }
    }
}

data class StatisticsTabState(
    val titles: List<Int>,
    val currentIndex: Int
)
