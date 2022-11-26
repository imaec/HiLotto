package com.imaec.hilotto.ui.home

import White
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = hiltViewModel()
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { padding ->
        HomeScreen(modifier = Modifier
            .fillMaxHeight()
            .padding(padding)
            .background(White)
        )
    }
}

@Composable
internal fun HomeScreen(modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            HomeCurrentRoundNumber()
        }
        item {
            HomeLatelyRoundResult()
        }
        homeStore()
    }
}

@Preview
@Composable
private fun HomeRoutePreview() {
    HomeRoute()
}
