package com.example.gamepadtcpudp.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun StatusConnection(isConnected: Boolean) {
    val statusText = if (isConnected) "🔵 Connesso" else "⚪ Non connesso"
    Text(statusText, color = if (isConnected) Color.Green else Color.Red)
}