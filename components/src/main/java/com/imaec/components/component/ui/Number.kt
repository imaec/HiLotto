package com.imaec.components.component.ui

import Color01_10
import Color01_10Dark
import White
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberCircle(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Surface(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = Color01_10,
                shape = CircleShape
            )
            .shadow(elevation = 2.dp, shape = CircleShape),
    ) {
        Box(
            modifier = Modifier.background(color = Color01_10),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                color = White,
                fontSize = fontSize,
                fontWeight = fontWeight,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NumberSquare(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .height(30.dp)
            .aspectRatio(1f)
            .background(color = Color01_10),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            color = White,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberCirclePreview() {
    NumberCircle(
        modifier = Modifier.size(50.dp),
        text = "1"
    )
}

@Preview(showBackground = true)
@Composable
fun NumberSquarePreview() {
    NumberSquare(
        modifier = Modifier.size(30.dp),
        text = "1"
    )
}
