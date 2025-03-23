package com.omasyo.currencycalculator.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.omasyo.currencycalculator.ui.theme.CurrencyCalculatorTheme


enum class DayRange(val text: String) { Thirty("Past 30 days"), Ninety("Past 90 days") }

@Composable
fun DayRangeSelector(
    dayRange: DayRange,
    onDayRangeChange: (DayRange) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabRow(
        selectedTabIndex = dayRange.ordinal,
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        indicator = { tabPositions ->
            DayRangeIndicator(tabPositions = tabPositions, dayRange = dayRange)
        },
        divider = {}
    ) {
        DayRange.entries.forEach {

            val alpha by animateFloatAsState(if (dayRange == it) 1f else 0.3f)
            Text(
                text = it.text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = LocalContentColor.current.copy(alpha),
                modifier = Modifier
                    .clickable(
                        interactionSource = null,
                        indication = null,
                    ) { onDayRangeChange(it) }
                    .padding(bottom = 8f.dp)
            )
        }
    }
}


@Composable
private fun DayRangeIndicator(
    tabPositions: List<TabPosition>,
    dayRange: DayRange
) {

    val tabPosition = tabPositions[dayRange.ordinal]
    val center = (tabPosition.right - tabPosition.left) / 2

    val offset by animateDpAsState(tabPosition.right - center)
    Box(
        Modifier
            .padding(top = 16f.dp)
            .wrapContentSize(align = Alignment.BottomStart)
            .offset {
                IntOffset(x = offset.roundToPx(), y = 0)
            }
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .size(8f.dp)
            .fillMaxSize()
    )
}

@Preview
@Composable
private fun Preview() {
    var dayRange by remember { mutableStateOf(DayRange.Thirty) }

    CurrencyCalculatorTheme {
        Surface {
            DayRangeSelector(
                dayRange = dayRange,
                onDayRangeChange = { dayRange = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24f.dp)
            )
        }
    }
}