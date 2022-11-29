package com.imaec.hilotto.ui.statistics.sum

import Gray200
import Gray33
import Gray400
import Gray500
import White
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
        Card(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(contentPadding = PaddingValues(vertical = 6.dp)) {
                    items(50) {
                        SumItem()
                    }
                }
                Divider(
                    modifier = Modifier
                        .padding(start = 80.dp)
                        .fillMaxHeight()
                        .width(1.dp),
                    color = Gray33
                )
            }
        }
        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                modifier = Modifier.padding(start = 80.dp),
                text = "21"
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = "255"
            )
        }
    }
}

@Composable
internal fun SumItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.width(80.dp),
            text = "999회",
            color = Gray400,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        LinearProgressIndicator(
            modifier = Modifier.weight(1f),
            progress = 0.5f,
            color = Color.Red
        )
        Text(
            modifier = Modifier.width(60.dp).padding(horizontal = 16.dp),
            text = "255",
            color = Gray500,
            fontSize = 14.sp,
            textAlign = TextAlign.End
        )
    }
}

@Preview
@Composable
fun SumScreenPreview() {
    SumScreen()
}
