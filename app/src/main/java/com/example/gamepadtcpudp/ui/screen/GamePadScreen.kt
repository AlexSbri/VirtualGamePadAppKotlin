package com.example.gamepadtcpudp.ui.screen

import VirtualJoystick
import android.R
import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gamepadtcpudp.tcpudp.AnalogController
import com.example.gamepadtcpudp.ui.components.ButtonCenters

import com.example.gamepadtcpudp.ui.components.ButtonConnect
import com.example.gamepadtcpudp.ui.components.ButtonRightScreen
import com.example.gamepadtcpudp.ui.components.ButtonShoulders
import com.example.gamepadtcpudp.ui.theme.GamePadTCPUDPTheme
import com.example.gamepadtcpudp.viewmodel.TcpConnectionViewModel

@Composable
fun GamePadScreen(tcpConnectionviewModel: TcpConnectionViewModel) {

    val context = LocalContext.current

    /*DisposableEffect(Unit) {
        val activity = context as? Activity
        if (activity != null) {
            val originalOrientation = activity.requestedOrientation
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            onDispose {
                // Ripristina l'orientamento originale quando si lascia lo screen
                activity.requestedOrientation = originalOrientation
            }
        }
        onDispose {  }
    }

     */
    val activity = context as? Activity

    val originalOrientation = remember {
        activity?.requestedOrientation ?: ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    DisposableEffect(Unit) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        onDispose {
            activity?.requestedOrientation = originalOrientation
        }
    }

    val controller = remember {
        AnalogController(tcpConnectionviewModel::sendKeyToPc)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ){
        VirtualJoystick(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(32.dp),
            onMove = controller::onAnalogMove)
        VirtualJoystick(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            onMove = controller::onRightAnalogMove
        )
        ButtonShoulders(tcpConnectionviewModel)
        ButtonRightScreen(tcpConnectionviewModel)
        ButtonCenters(tcpConnectionviewModel)
    }

}

@Composable
fun GamePadScreenTest(tcpConnectionviewModel: TcpConnectionViewModel) {

    val context = LocalContext.current

    DisposableEffect(Unit) {
        val activity = context as? Activity
        if (activity != null) {
            val originalOrientation = activity.requestedOrientation
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            onDispose {
                // Ripristina l'orientamento originale quando si lascia lo screen
                activity.requestedOrientation = originalOrientation
            }
        }
        onDispose {  }
    }


    val controller = remember {
        AnalogController(tcpConnectionviewModel::sendKeyToPc)
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        VirtualJoystick(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(32.dp),
            onMove = controller::onAnalogMove)
        ButtonShoulders(tcpConnectionviewModel)
        ButtonRightScreen(tcpConnectionviewModel)
        ButtonCenters(tcpConnectionviewModel)
    }

}

@Composable
fun testJetpack(){
    Box (
        modifier = Modifier
            .size(200.dp)
            .background(Color.Red)
    ){
        Column {
            Button(onClick = {}){}
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = {}){}
        }
    }
}
