package com.example.gamepadtcpudp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import com.example.gamepadtcpudp.R
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun GamepadIcon(){
    /*Icon(
        Icons.Filled.Email, //da cambiare in un GamePad
        contentDescription = "GamePad",
        modifier = Modifier.size(128.dp)
    )

     */
    Image(
        painter = painterResource(id = R.drawable.game_pad),
        contentDescription = "Gamepad Icon",
        modifier = Modifier
            .size(128.dp) // regola la dimensione come preferisci
            .padding(16.dp)
    )
}

