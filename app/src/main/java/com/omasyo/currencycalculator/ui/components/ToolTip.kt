package com.omasyo.currencycalculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omasyo.currencycalculator.ui.theme.CurrencyCalculatorTheme

@Composable
fun ToolTip(
    currency: String,
    conversionRate: String,
    date: String,
    anchor: ToolTipAnchor,
    modifier: Modifier = Modifier,
) {
    val cornerRadius = MaterialTheme.shapes.small.topEnd
    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = if(anchor.topStart) ZeroCornerSize  else cornerRadius,
                    topEnd = if(anchor.topEnd) ZeroCornerSize else cornerRadius,
                    bottomStart = if(anchor.bottomStart) ZeroCornerSize else cornerRadius,
                    bottomEnd = if(anchor.bottomEnd) ZeroCornerSize else cornerRadius,
                )
            )
            .background(MaterialTheme.colorScheme.secondary)
            .padding(8f.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSecondary) {
            Text(
                text = date,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = "1 $currency = $conversionRate",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CurrencyCalculatorTheme {
        Surface {
            ToolTip(
                currency = "EUR",
                conversionRate = "4.242",
                date = "15 Jun",
                anchor = ToolTipAnchor(anchorLeft = true, anchorBottom = true),
                modifier = Modifier.padding(24f.dp)
            )
        }
    }
}