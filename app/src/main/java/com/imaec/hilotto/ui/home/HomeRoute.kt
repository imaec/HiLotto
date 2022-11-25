package com.imaec.hilotto.ui.home

import Color01_10
import Gray400
import Gray500
import Gray700
import Gray900
import White
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        HomeScreen(modifier = Modifier.padding(padding))
    }
}

@Composable
internal fun HomeScreen(modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        item {
            HomeCurrentRoundNumber()
        }
    }

}

@Composable
internal fun HomeCurrentRoundNumber() {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = "2022-11-24",
            color = Gray500,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "1017회 당첨번호",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Row(Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)) {
            Number(text = "1")
            Number(text = "2")
            Number(text = "3")
            Number(text = "4")
            Number(text = "5")
            Number(text = "6")
        }
        Row(Modifier.padding(top = 12.dp, start = 16.dp, end = 16.dp)) {
            Box(
                modifier = Modifier
                    .weight(4f)
                    .padding(horizontal = 16.dp)
                    .aspectRatio(4f)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "BONUS",
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    color = Gray700,
                    fontSize =12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }
            Number(text = "7")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = "1등 당첨자 수",
                color = Gray700,
                fontSize = 14.sp
            )
            Text(
                text = "14명",
                color = Gray700,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(top = 4.dp, end = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(0.3f))
            Row(
                modifier = Modifier
                    .weight(0.7f)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .border(
                        width = 1.dp,
                        color = Gray400,
                        shape = RoundedCornerShape(5.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .fillMaxHeight()
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 5.dp,
                                bottomStart = 5.dp
                            )
                        )
                        .background(Gray900),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1등",
                        color = White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    text = "1,811,116,822 (약 18억)",
                    color = Gray500,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "원",
                color = Gray700,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
internal fun RowScope.Number(text: String) {
    Box(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp)
            .aspectRatio(1f)
            .background(
                color = Color01_10,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            color = White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun HomeRoutePreview() {
    HomeRoute()
}
