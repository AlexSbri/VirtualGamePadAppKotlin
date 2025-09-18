package com.example.gamepadtcpudp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import com.example.gamepadtcpudp.ui.theme.GamePadTCPUDPTheme

@Composable
    fun CustomTextFieldGamePad(text : String, placeholder: String, onValueChange : (String) -> Unit){
        //var text by remember { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange= onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Black,
                unfocusedTextColor = Color.Gray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
    }

    @Preview
    @Composable
    fun CustomTextFieldGamePadPreview(){
        GamePadTCPUDPTheme {
            var text by remember { mutableStateOf("") }
            CustomTextFieldGamePad(
                text = text,
                placeholder = "127.0.0.1",
                onValueChange = {text = it}
            )
        }
    }
