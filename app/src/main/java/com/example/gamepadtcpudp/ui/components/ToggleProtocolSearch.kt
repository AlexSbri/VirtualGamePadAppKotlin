package com.example.gamepadtcpudp.ui.components

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gamepadtcpudp.tcpudp.UdpDiscoverPc
import com.example.gamepadtcpudp.ui.theme.GamePadTCPUDPTheme
import com.example.gamepadtcpudp.viewmodel.TcpConnectionViewModel
import com.example.gamepadtcpudp.viewmodel.UdpDiscoverPcViewModel

@Composable
fun ToggleProtocoSearch(viewModel: UdpDiscoverPcViewModel){
    var selectedProtocol by remember { mutableStateOf("TCP") }
    Column {
        if (selectedProtocol == "TCP") tcpForm()
        else udpButton(viewModel)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { selectedProtocol = "TCP" },
                colors = ButtonDefaults.buttonColors(
                    if (selectedProtocol == "TCP")
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("TCP")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { selectedProtocol = "UDP"},
                colors = ButtonDefaults.buttonColors(
                    if (selectedProtocol == "UDP") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("UDP")
            }

        }

    }

}

@Composable
fun tcpForm(){
    Column {
        Label("Indirizzo IP")
        CustomTextFieldGamePad(text = "", placeholder = "a27ajas", onValueChange = {})
        Label("Porta")
        CustomTextFieldGamePad(text = "", placeholder = "a27ajas", onValueChange = {})
    }
}

@Composable
fun udpButton(viewModel: UdpDiscoverPcViewModel){
    ButtonDiscovery {
        viewModel.DiscoverPc()
    }
}