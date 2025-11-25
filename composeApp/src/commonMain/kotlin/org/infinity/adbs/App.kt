package org.infinity.adbs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.infinity.lib.DateConverter
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var date by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            val convertedDateBS = DateConverter.adToBs(1990, 6, 22)
            println("Converted Date in bs: $convertedDateBS")
            val convertedDateAD = DateConverter.bsToAd(
                convertedDateBS.year,
                convertedDateBS.month,
                convertedDateBS.day
            )
            println("Converted Date in ad: $convertedDateAD")
            date = convertedDateAD.toString()
        }

        Text(
            date
        )
    }
}