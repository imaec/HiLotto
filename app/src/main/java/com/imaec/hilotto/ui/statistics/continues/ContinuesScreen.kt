package com.imaec.hilotto.ui.statistics.continues

import Gray33
import Gray500
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imaec.components.component.ui.NumberCircle

@Composable
internal fun ContinuesScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
            items(5) {
                ContinuesItem()
            }
        }
    }
}

@Composable
private fun ContinuesItem() {
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
            items(6) {
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
            modifier = Modifier.width(94.dp),
            text = "연속번호 없음",
            color = Gray500,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContinuesScreenPreview() {
    ContinuesScreen()
}
