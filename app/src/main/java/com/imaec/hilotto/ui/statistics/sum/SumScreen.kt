package com.imaec.hilotto.ui.statistics.sum

import Gray200
import Gray33
import Gray500
import White
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SumScreen() {
    Column(modifier = Modifier.background(Gray200)) {
        Card(
            modifier = Modifier.padding(bottom = 8.dp),
            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "10회 합계 평균 : 141",
                    color = Gray500,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    modifier = Modifier.size(24.dp),
                    checked = false,
                    onCheckedChange = {}
                )
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = "보너스 번호 포함",
                    color = Gray33,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun SumScreenPreview() {
    SumScreen()
}
