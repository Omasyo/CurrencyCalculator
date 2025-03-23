package com.omasyo.currencycalculator.ui.convert

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performKeyPress
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.TextFieldValue
import com.omasyo.currencycalculator.model.ChartPoint
import com.omasyo.currencycalculator.model.UiState
import com.omasyo.currencycalculator.ui.components.DayRange
import com.omasyo.currencycalculator.ui.theme.CurrencyCalculatorTheme
import org.junit.Rule
import org.junit.Test

class ConvertCurrencyScreenKtTest {


    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {

            var dayRange by remember { mutableStateOf(DayRange.Thirty) }
            CurrencyCalculatorTheme {
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
                    chartData = UiState.Success(
                        listOf(
                            ChartPoint("Jan", 10.0),
                            ChartPoint("Feb", 23.0),
                            ChartPoint("Mar", 7.0),
                            ChartPoint("Apr", 42.0),
                            ChartPoint("May", 34.0),
                            ChartPoint("Jun", 52.0),
                            ChartPoint("Jul", 12.0),
                            ChartPoint("Aug", 6.0),
                            ChartPoint("Sep", 0.0),
                            ChartPoint("Oct", 0.0),
                            ChartPoint("Nov", 0.0),
                            ChartPoint("Dec", 0.0),
                        )
                    ),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

      val fromCurrencyTextField =  composeTestRule.onNodeWithTag("from_field")
          fromCurrencyTextField.assert(hasAnyDescendant(hasText("EUR")))
    }
}