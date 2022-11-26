package com.imaec.hilotto.ui.home

import Gray200
import Gray400
import Gray700
import White
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun HomeLatelyRoundResult() {
    Column(modifier = Modifier.background(Gray200)) {
        Card(
            modifier = Modifier.padding(bottom = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "최근 회차 결과",
                    color = Gray700,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = "더보기",
                    color = Gray400,
                    fontSize = 14.sp
                )
            }
            LazyRow(
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(10) {
                    HomeLatelyRoundResultItem()
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeLatelyRoundResultPreview() {
    HomeLatelyRoundResult()
}
