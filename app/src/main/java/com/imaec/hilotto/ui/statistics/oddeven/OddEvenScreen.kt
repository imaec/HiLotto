package com.imaec.hilotto.ui.statistics.oddeven

import Gray33
import Gray500
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imaec.components.component.ui.NumberCircle

@Composable
internal fun OddEvenScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
            items(5) {
                OddEvenItem()
            }
        }
    }
}

@Composable
private fun OddEvenItem() {
    Row(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .width(60.dp)
                .padding(vertical = 4.dp),
            text = "999회",
            color = Gray33,
            fontSize = 15.sp
        )
        LazyRow(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            userScrollEnabled = false
        ) {
            items(3) {
                NumberCircle(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(24.dp),
                    text = "4",
                    fontSize = 12.sp
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(24.dp)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = ":",
                        style = TextStyle(
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        ),
                        color = Gray33,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            items(3) {
                NumberCircle(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(24.dp),
                    text = "4",
                    fontSize = 12.sp
                )
            }
        }
        Text(
            modifier = Modifier.width(48.dp),
            text = "3 : 3",
            color = Gray500,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OddEvenScreenPreview() {
    OddEvenScreen()
}
