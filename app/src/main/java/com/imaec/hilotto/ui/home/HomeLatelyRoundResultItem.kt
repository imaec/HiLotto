package com.imaec.hilotto.ui.home

import Color01_10Dark
import Gray500
import Gray700
import White
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.imaec.components.component.ui.NumberSquare

@Composable
internal fun HomeLatelyRoundResultItem() {
    Card(
        modifier = Modifier
            .padding(horizontal = 7.5.dp)
            .width(180.dp)
            .height(130.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row {
            NumberSquare(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color01_10Dark,
                        shape = RoundedCornerShape(topStart = 5.dp)
                    ),
                text = "1"
            )
            NumberSquare(
                modifier = Modifier
                    .weight(1f)
                    .border(width = 1.dp, color = Color01_10Dark),
                text = "2"
            )
            NumberSquare(
                modifier = Modifier
                    .weight(1f)
                    .border(width = 1.dp, color = Color01_10Dark),
                text = "3"
            )
            NumberSquare(
                modifier = Modifier
                    .weight(1f)
                    .border(width = 1.dp, color = Color01_10Dark),
                text = "4"
            )
            NumberSquare(
                modifier = Modifier
                    .weight(1f)
                    .border(width = 1.dp, color = Color01_10Dark),
                text = "5"
            )
            NumberSquare(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color01_10Dark,
                        shape = RoundedCornerShape(topEnd = 5.dp)
                    ),
                text = "6"
            )
        }
        Row {
            Box(
                modifier = Modifier
                    .weight(4f)
                    .aspectRatio(4f)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(30.dp)
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
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            NumberSquare(
                modifier = Modifier
                    .weight(1f)
                    .border(width = 1.dp, color = Color01_10Dark),
                text = "7"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "999회",
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                color = Gray700,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = "1인당 약 31억원",
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                color = Gray500,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeLatelyRoundResultItemPreview() {
    HomeLatelyRoundResultItem()
}
