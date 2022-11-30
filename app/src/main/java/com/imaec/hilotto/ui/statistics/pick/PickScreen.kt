package com.imaec.hilotto.ui.statistics.pick

import Gray200
import Gray33
import Gray500
import White
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imaec.components.component.ui.NumberCircle

@Composable
internal fun PickScreen() {
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
                    text = "20회 출현/미출현",
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
            modifier = Modifier.padding(bottom = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        bottom = 24.dp,
                        start = 12.dp,
                        end = 12.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "구간별 출현횟수",
                    color = Gray33,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn {
                    items(5) {
                        PickItem()
                    }
                }
            }
        }
        Card(
            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 16.dp,
                        bottom = 24.dp,
                        start = 12.dp,
                        end = 12.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "구간별 출현횟수",
                    color = Gray33,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn {
                    items(5) {
                        NoPickItem()
                    }
                }
            }
        }
    }
}

@Composable
private fun PickItem() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .width(80.dp)
                .padding(vertical = 4.dp),
            text = "1~10번",
            color = Gray33,
            fontSize = 15.sp
        )
        LinearProgressIndicator(
            modifier = Modifier.weight(1f),
            progress = 0.5f,
            color = Color.Red
        )
        Text(
            modifier = Modifier.width(62.dp),
            text = "30",
            color = Gray500,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun NoPickItem() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .width(80.dp)
                .padding(vertical = 4.dp),
            text = "1~10번",
            color = Gray33,
            fontSize = 15.sp
        )
        LazyRow {
            items(7) {
                NumberCircle(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(24.dp),
                    text = "4",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun PickScreenPreview() {
    PickScreen()
}
