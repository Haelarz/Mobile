package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TipCalculator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculator() {
    var costInput by remember { mutableStateOf("") }
    var selectedTip by remember { mutableStateOf(10)}
    var roundUp by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val cost = costInput.toDoubleOrNull() ?: 0.0
    val tipOptions = listOf(10, 15, 20)
    var tipAmount = cost * selectedTip / 100

    if (roundUp) {
        tipAmount = ceil(tipAmount)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Tip Calculator",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = costInput,
            onValueChange = { costInput = it },
            label = { Text("Bill Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(value = "$selectedTip%",
                onValueChange = {},
                readOnly = true,
                label = { Text("Tip Percentage") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text("$option%") },
                        onClick = {
                            selectedTip = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Round Up Tip")
            Switch(
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        Text(
            text = "Tip Amount : $${tipAmount.toFloat()}",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipCalculatorPreview(){
    TipCalculator()
}