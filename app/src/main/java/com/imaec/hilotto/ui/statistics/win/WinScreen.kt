package com.imaec.hilotto.ui.statistics.win

import Gray200
import Gray33
import Gray500
import White
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun WinScreen() {
    Column(modifier = Modifier.background(Gray200)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 12.dp,
                    start = 12.dp,
                    end = 12.dp
                )
            ) {
                Text(
                    text = "1등 당첨금 평균 (20회)",
                    color = Gray33,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(3) {
                        Column {
                            Text(
                                text = "총 당첨금",
                                color = Gray500,
                                fontSize = 13.sp
                            )
                            Text(
                                modifier = Modifier.padding(top = 2.dp),
                                text = "24,477,035,786 (약 244억)",
                                color = Gray33,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 12.dp,
                    start = 12.dp,
                    end = 12.dp
                )
            ) {
                Text(
                    text = "1등 당첨금 평균 (20회)",
                    color = Gray33,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(4) {
                        Column {
                            Text(
                                text = "총 당첨금",
                                color = Gray500,
                                fontSize = 13.sp
                            )
                            Text(
                                modifier = Modifier.padding(top = 2.dp),
                                text = "24,477,035,786 (약 244억)",
                                color = Gray33,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun WinScreenPreview() {
    WinScreen()
}
