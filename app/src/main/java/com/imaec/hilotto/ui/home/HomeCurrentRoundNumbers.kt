package com.imaec.hilotto.ui.home

import Gray200
import Gray33
import Gray400
import Gray500
import Gray700
import White
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.imaec.components.component.ui.NumberCircle

@Composable
internal fun HomeCurrentRoundNumber() {
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
                NumberCircle(modifier = Modifier.weight(1f).padding(horizontal = 4.dp), text = "1")
                NumberCircle(modifier = Modifier.weight(1f).padding(horizontal = 4.dp), text = "2")
                NumberCircle(modifier = Modifier.weight(1f).padding(horizontal = 4.dp), text = "3")
                NumberCircle(modifier = Modifier.weight(1f).padding(horizontal = 4.dp), text = "4")
                NumberCircle(modifier = Modifier.weight(1f).padding(horizontal = 4.dp), text = "5")
                NumberCircle(modifier = Modifier.weight(1f).padding(horizontal = 4.dp), text = "6")
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
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                }
                NumberCircle(modifier = Modifier.weight(1f).padding(horizontal = 4.dp), text = "7")
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
                    .padding(bottom = 32.dp)
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
                            .background(Gray33),
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
}

@Preview(showBackground = true)
@Composable
fun HomeCurrentRoundNumbersPreview() {
    HomeCurrentRoundNumber()
}
