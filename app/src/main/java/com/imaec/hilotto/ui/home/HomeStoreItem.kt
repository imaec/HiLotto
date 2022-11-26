package com.imaec.hilotto.ui.home

import Gray400
import Gray500
import Gray700
import White
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun HomeStoreItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.5.dp
        )
    ) {
        Row(modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp)) {
            Text(
                text = "로또 판매점",
                color = Gray700,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "자동",
                color = Gray500,
                fontSize = 11.sp
            )
        }
        Text(
            modifier = Modifier.padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
            text = "서울시 강남구",
            color = Gray400,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeStoreItemPreview() {
    HomeStoreItem()
}
