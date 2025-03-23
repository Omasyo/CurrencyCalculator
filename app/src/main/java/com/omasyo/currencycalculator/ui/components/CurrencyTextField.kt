package com.omasyo.currencycalculator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omasyo.currencycalculator.ui.theme.CurrencyCalculatorTheme

@Composable
fun CurrencyTextField(
    value: String,
    onValueChange: (String) -> Unit,
    currency: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16f.dp),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.titleMedium,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.weight(1f),
        ) { innerTextField ->
            if (value.isEmpty()) Text(
                "0.00",
                style = MaterialTheme.typography.titleMedium,
                color = LocalContentColor.current.copy(0.3f)
            )
            innerTextField()
        }
        Text(
            text = currency,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    var value by remember { mutableStateOf("") }

    CurrencyCalculatorTheme {
        Surface {
            CurrencyTextField(
                value = value,
                onValueChange = { value = it },
                currency = "NGN",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24f.dp)
            )
        }
    }
}