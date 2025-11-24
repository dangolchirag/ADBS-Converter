package org.infinity.adbs

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ADBS Converter",
    ) {
        App()
    }
}