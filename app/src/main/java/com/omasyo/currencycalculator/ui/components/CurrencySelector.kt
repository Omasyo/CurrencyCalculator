package com.omasyo.currencycalculator.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omasyo.currencycalculator.R
import com.omasyo.currencycalculator.ui.theme.CurrencyCalculatorTheme

@Composable
fun CurrencySelector(
    fromCurrency: String,
    onFromCurrencyChange: (String) -> Unit,
    toCurrency: String,
    onToCurrencyChange: (String) -> Unit,
    onSwapCurrencies: () -> Unit,
    currencyOptions: List<String>,
    isFromSelectorEnabled: Boolean,
    isToSelectorEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8f.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        CurrencyField(
            currency = fromCurrency,
            onCurrencyChange = onFromCurrencyChange,
            currencyOptions = currencyOptions,
            isEnabled = isFromSelectorEnabled,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onSwapCurrencies) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_swap),
                contentDescription = "Swap Currencies"
            )
        }
        CurrencyField(
            currency = toCurrency,
            onCurrencyChange = onToCurrencyChange,
            currencyOptions = currencyOptions,
            isEnabled = isToSelectorEnabled,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyField(
    currency: String,
    onCurrencyChange: (String) -> Unit,
    currencyOptions: List<String>,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
        ) {

            Row(
                modifier = modifier
                    .border(
                        1f.dp,
                        MaterialTheme.colorScheme.outline,
                        MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 16f.dp, 12f.dp),
            ) {
                Text(
                    text = currency,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                )
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0f.dp,
            shadowElevation = 2f.dp,
            modifier = Modifier
                .exposedDropdownSize()
                .fillMaxWidth()
        ) {
            for (option in currencyOptions) {
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500)
                        )
                    },
                    enabled = isEnabled,
                    onClick = {
                        onCurrencyChange(option)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    var from by remember { mutableStateOf("EUR") }
    var to by remember { mutableStateOf("PLN") }

    CurrencyCalculatorTheme {
        Surface {
            CurrencySelector(
                fromCurrency = from,
                onFromCurrencyChange = { from = it },
                toCurrency = to,
                onToCurrencyChange = { to = it },
                onSwapCurrencies = {
                    val temp = from
                    from = to
                    to = temp
                },
                currencyOptions = listOf("EUR", "PLN", "NGN", "USD", "CFC", "CAD"),
                isFromSelectorEnabled = false,
                isToSelectorEnabled = true,
                modifier = Modifier
                    .padding(24f.dp)
                    .fillMaxWidth()
                    .height(320f.dp)
//                    .fillMaxHeight()
            )
        }
    }
}