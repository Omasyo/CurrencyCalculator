package com.omasyo.currencycalculator.ui.convert

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omasyo.currencycalculator.model.ChartPoint
import com.omasyo.currencycalculator.model.UiState
import com.omasyo.currencycalculator.ui.components.CurrencySelector
import com.omasyo.currencycalculator.ui.components.CurrencyTextField
import com.omasyo.currencycalculator.ui.components.DayRange
import com.omasyo.currencycalculator.ui.components.DayRangeSelector
import com.omasyo.currencycalculator.ui.components.LineChart
import com.omasyo.currencycalculator.ui.components.ToolTip
import com.omasyo.currencycalculator.ui.theme.CurrencyCalculatorTheme

@Composable
fun ConvertCurrencyScreen(
    onHistoryTap: () -> Unit,
    onFromAmountChange: (String) -> Unit,
    onToAmountChange: (String) -> Unit,
    onFromCurrencyChange: (String) -> Unit,
    onToCurrencyChange: (String) -> Unit,
    currencyUiState: CurrencyUiState,
    onConvertTap: () -> Unit,
    onSwapCurrencies: () -> Unit,
    dayRange: DayRange,
    onDayRangeChange: (DayRange) -> Unit,
    currencyOptions: List<String>,
    chartData: UiState<List<ChartPoint>>,
    modifier: Modifier = Modifier,
) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        Column(
            Modifier
                .padding(24f.dp)
                .padding(bottom = 24f.dp)
        ) {
            TextButton(
                onClick = onHistoryTap,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    "History",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.height(100f.dp))
            Text(
                text = buildAnnotatedString {
                    append("Currency Calculator")
                    append(
                        AnnotatedString(
                            ".",
                            spanStyle = SpanStyle(color = MaterialTheme.colorScheme.secondary)
                        )
                    )
                },
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(56f.dp))
            CurrencyTextField(
                value = currencyUiState.from.amount,
                onValueChange = onFromAmountChange,
                currency = currencyUiState.from.currency,
                modifier = Modifier
                    .testTag("from_field")
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(16f.dp))
            CurrencyTextField(
                value = currencyUiState.to.amount,
                onValueChange = onToAmountChange,
                currency = currencyUiState.to.currency,
                modifier = Modifier
                    .testTag("to_field")
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(32f.dp))
            CurrencySelector(
                fromCurrency = currencyUiState.from.currency,
                onFromCurrencyChange = onFromCurrencyChange,
                toCurrency = currencyUiState.to.currency,
                onToCurrencyChange = onToCurrencyChange,
                onSwapCurrencies = onSwapCurrencies,
                currencyOptions = currencyOptions,
                isFromSelectorEnabled = currencyUiState.from.isEnabled,
                isToSelectorEnabled = currencyUiState.to.isEnabled,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(24f.dp))
            FilledTonalButton(
                onClick = onConvertTap,
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                ),
                contentPadding = PaddingValues(horizontal = 24f.dp, vertical = 12f.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Convert", style = MaterialTheme.typography.titleMedium)
            }
        }
        Column(
            modifier = Modifier
                .clip(
                    MaterialTheme.shapes.extraLarge.copy(
                        bottomStart = ZeroCornerSize,
                        bottomEnd = ZeroCornerSize,
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .padding(horizontal = 24f.dp, vertical = 32f.dp),
        ) {
            DayRangeSelector(
                dayRange = dayRange,
                onDayRangeChange = onDayRangeChange,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(32f.dp))
            Surface(
                color = MaterialTheme.colorScheme.primary
            ) {
                AnimatedContent(chartData) { chartData ->
                    when (chartData) {
                        is UiState.Error -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400f.dp),
//                                verticalArrangement = Arrangement.Center,
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    chartData.message,
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                )
                            }
                        }

                        UiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400f.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                            }
                        }

                        is UiState.Success -> {
                            LineChart(
                                data = chartData.content.associate {
                                    Pair(
                                        it.date,
                                        it.conversionRate
                                    )
                                },
                                modifier = Modifier
                                    .height(400f.dp)
                                    .fillMaxWidth()
                                    .padding(8f.dp)
                            ) { index, anchor ->
                                ToolTip(
                                    currency = currencyUiState.from.currency,
                                    conversionRate = chartData.content[index].conversionRate.toString(),
                                    anchor = anchor,
                                    date = chartData.content[index].date,
                                    modifier = Modifier.padding(
                                        top = if (!anchor.anchorBottom) 8f.dp else 0f.dp,
                                        bottom = if (anchor.anchorBottom) 8f.dp else 0f.dp,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {

    var dayRange by remember { mutableStateOf(DayRange.Thirty) }
    CurrencyCalculatorTheme {
        Surface {
            ConvertCurrencyScreen(
                onHistoryTap = {},
                onFromAmountChange = {},
                onToAmountChange = {},
                onFromCurrencyChange = {},
                onToCurrencyChange = {},
                currencyUiState = CurrencyUiState(
                    Currency(
                        amount = "1",
                        currency = "EUR",
                        isEnabled = false,
                    ),
                    Currency(
                        amount = "4.264820",
                        currency = "PLN",
                        isEnabled = true,
                    ),
                ),
                onConvertTap = {},
                onSwapCurrencies = {},
                dayRange = dayRange,
                onDayRangeChange = { dayRange = it },
                currencyOptions = listOf("EUR", "PLN", "NGN", "USD", "CFC", "CAD"),
                chartData = UiState.Error("An Error Occurred"),
//                UiState.Success(
//                    listOf(
//                        ChartPoint("Jan", 10.0),
//                        ChartPoint("Feb", 23.0),
//                        ChartPoint("Mar", 7.0),
//                        ChartPoint("Apr", 42.0),
//                        ChartPoint("May", 34.0),
//                        ChartPoint("Jun", 52.0),
//                        ChartPoint("Jul", 12.0),
//                        ChartPoint("Aug", 6.0),
//                        ChartPoint("Sep", 0.0),
//                        ChartPoint("Oct", 0.0),
//                        ChartPoint("Nov", 0.0),
//                        ChartPoint("Dec", 0.0),
//                    )
//                ),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}