package com.example.gamepadtcpudp.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.gamepadtcpudp.tcpudp.TcpConnection

import com.example.gamepadtcpudp.ui.components.ButtonConnect
import com.example.gamepadtcpudp.ui.components.ButtonDiscovery
import com.example.gamepadtcpudp.ui.components.ButtonOnPressOnTap
import com.example.gamepadtcpudp.ui.components.ButtonToGamePad
import com.example.gamepadtcpudp.ui.components.GamepadIcon

import com.example.gamepadtcpudp.ui.components.StatusConnection
import com.example.gamepadtcpudp.ui.components.TopBarGamePad
import com.example.gamepadtcpudp.ui.theme.GamePadTCPUDPTheme
import com.example.gamepadtcpudp.viewmodel.TcpConnectionViewModel
import com.example.gamepadtcpudp.viewmodel.UdpDiscoverPcViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent
import com.example.gamepadtcpudp.R
import com.example.gamepadtcpudp.tcpudp.UdpDiscoverResult


@Composable
fun ConnectionScreenView(navController: NavHostController,tcpConnectionviewModel: TcpConnectionViewModel ) {
    val udpDiscoverPcViewModel : UdpDiscoverPcViewModel = viewModel()
    val isConnected by tcpConnectionviewModel.isConnected.collectAsState()
    val result by udpDiscoverPcViewModel.discoverResult.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val canConnect = remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { TopBarGamePad("Connessione PC") },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        innerpadding ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            //ToggleProtocoSearch(udpDiscoverPcViewModel)
            ButtonDiscovery {
                udpDiscoverPcViewModel.discoverPc()
            }

            LaunchedEffect(result) {
                when(result){
                    is UdpDiscoverResult.Found -> {
                        snackbarHostState.showSnackbar("Pc trovato")
                        canConnect.value = true
                    }
                    is UdpDiscoverResult.NotFound -> {
                        snackbarHostState.showSnackbar("Pc non trovato trovato, riprova Trova Pc")
                        canConnect.value=false
                    }
                    UdpDiscoverResult.AwaitingDiscovery -> {
                        snackbarHostState.showSnackbar("Attesa della ricerca del Pc")
                        canConnect.value=false
                    }
                }
            }


            ButtonConnect(enabled = canConnect.value ) {tcpConnectionviewModel.connectToPc(udpDiscoverPc = result)}
            GamepadIcon()
            Spacer(modifier = Modifier.height(8.dp))
            StatusConnection(isConnected)

            ButtonToGamePad {
                navController.navigate("GamePad")
            }

        }
    }
    }
}

@Preview
@Composable
fun ConnectionScreenViewPreview(){
    GamePadTCPUDPTheme {
        //ConnectionScreenView(navController = rememberNavController()) // remeberNavController è finto
    }
}

@Composable
fun HapticButton() {
    val haptic = LocalHapticFeedback.current

    Box(
        modifier = Modifier
            .size(200.dp) // Imposta la dimensione del pulsante
            .clip(CircleShape) // Rendi il pulsante circolare
            .background(Color.Blue) // Colore di sfondo
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        // Quando l'utente tiene premuto il tasto
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        tryAwaitRelease() // Attendi che il tasto venga rilasciato
                    },
                    onTap = {
                        // Aggiungi altre azioni se necessario, ad esempio al click
                        Log.d("HapticButton", "Button clicked!")
                    }
                )
            },
        contentAlignment = Alignment.Center // Allinea il contenuto (testo) al centro
    ) {
        Text(
            text = "Press Me",
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 10.dp)
        ) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}



