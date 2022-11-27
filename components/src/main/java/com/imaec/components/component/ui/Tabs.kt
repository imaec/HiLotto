package com.imaec.components.component.ui

import Gray33
import Gray400
import Outline60
import White
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HlSlideTab(
    modifier: Modifier = Modifier,
    tabList: List<String>,
    hasDivider: Boolean = false,
    selectedIndex: Int,
    onClickTab: (Int) -> Unit = {}
) {
    val activeIndicatorTextWidth = MutableList(tabList.size) { 0f }.toMutableStateList()

    Column(
        modifier = modifier.then(Modifier.fillMaxWidth())
    ) {
        val density = LocalDensity.current.density
        val context = LocalDensity.current
        HlScrollableTabRow(
            modifier = Modifier.padding(horizontal = 0.dp),
            backgroundColor = Gray33,
            selectedTabIndex = selectedIndex,
            divider = {
                HlTabRowDefaults.Divider(
                    thickness = 0.dp,
                    color = White
                )
            },
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                HlTabRowDefaults.Indicator(
                    color = Color.White,
                    height = 3.dp,
                    modifier = Modifier
                        .ownTabIndicatorOffset(
                            currentTabPosition = tabPositions[selectedIndex],
                            currentTabWidth = activeIndicatorTextWidth[selectedIndex].asPxToDP(density)
                        )
                )
            }
        ) {
            tabList.forEachIndexed { index, tab ->
                val selected = selectedIndex == index
                HlTab(
                    modifier = Modifier
                        .height(56.dp)
                        .padding(horizontal = 0.dp)
                        .onGloballyPositioned { coordinates ->
                            activeIndicatorTextWidth[index] =
                                coordinates.size.width.toFloat() - with(context) { 24.dp.toPx() }
                        },
                    selected = selected,
                    onClick = { onClickTab(index) }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = tab,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = if (selectedIndex == index) White else Gray400,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        if (hasDivider) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                color = Outline60
            )
        }
    }
}

fun Modifier.ownTabIndicatorOffset(
    currentTabPosition: TabPosition,
    currentTabWidth: Dp = currentTabPosition.width
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 0, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset + ((currentTabPosition.width - currentTabWidth) / 2))
        .width(currentTabWidth)
}

fun Float.asPxToDP(density: Float): Dp {
    return (this / (density)).dp
}

@Preview
@Composable
private fun HlSlideTabPreview() {
    val list = listOf(
        "합계", "출현/미출현", "연속번호", "홀짝", "당첨"
    )
    HlSlideTab(
        tabList = list,
        hasDivider = true,
        selectedIndex = 0,
    )
}
