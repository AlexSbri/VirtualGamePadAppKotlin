package com.example.gamepadtcpudp.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gamepadtcpudp.ui.theme.GamePadTCPUDPTheme
import com.example.gamepadtcpudp.viewmodel.TcpConnectionViewModel


@Composable
fun ButtonRightScreen(viewModel: TcpConnectionViewModel){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        contentAlignment = Alignment.BottomEnd
    ){
        Column(
            //verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Row {
                Spacer(modifier = Modifier.size(64.dp))
                ButtonOnPressOnTap( // GamePad : Y
                    key="D",
                    onPress = {viewModel.sendKeyToPc(it)},
                    onTap = {viewModel.sendKeyToPc(it)}
                )
                Spacer(modifier = Modifier.size(64.dp))
            }
            Row {
                ButtonOnPressOnTap( // GamePad : X
                    key="X",
                    onPress = {viewModel.sendKeyToPc(it)},
                    onTap = {viewModel.sendKeyToPc(it)}
                )
                Spacer(modifier = Modifier.size(64.dp))
                ButtonOnPressOnTap( // GamePad : B
                    key="A",
                    onPress = {viewModel.sendKeyToPc(it)},
                    onTap = {viewModel.sendKeyToPc(it)}
                )
            }
            Row {
                Spacer(modifier = Modifier.size(64.dp))
                ButtonOnPressOnTap( // GamePad : A
                    key="Z",
                    onPress = {viewModel.sendKeyToPc(it)},
                    onTap = {viewModel.sendKeyToPc(it)}
                )
                Spacer(modifier = Modifier.size(64.dp))
            }

        }
    }
}


@Composable
fun ButtonOnPressOnTap(
    key: String,
    onPress: (String) -> Unit,
    onTap: (String) -> Unit,

){
    val haptic = LocalHapticFeedback.current
    val onPressedColor = MaterialTheme.colorScheme.onSurface
    val notPressedColor = MaterialTheme.colorScheme.surface

    val color = remember { mutableStateOf(notPressedColor)}

    Box(
        modifier = Modifier
            .size(70.dp)
            .clip(CircleShape)
            .background(color = color.value)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        color.value = onPressedColor
                        onPress("${key}_DOWN")
                        tryAwaitRelease()
                        onPress("${key}_UP")
                        color.value = notPressedColor
                    }, onTap = {
                        color.value = onPressedColor
                        onTap("${key}_CLICKED")
                        color.value = notPressedColor
                    }
                )
            }
    ) {

    }
}

@Composable
fun ButtonShoulderOnPressOnTap(
    key: String,
    onPress: (String) -> Unit,
    onTap: (String) -> Unit,

    ){
    val haptic = LocalHapticFeedback.current

    val onPressedColor = MaterialTheme.colorScheme.surface
    val notPressedColor = MaterialTheme.colorScheme.onSurface

    val color = remember { mutableStateOf(notPressedColor)}
    Box(
        modifier = Modifier
            .size(width = 80.dp, height = 40.dp)
            .background(color = color.value)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        color.value = onPressedColor
                        onPress("${key}_DOWN")

                        tryAwaitRelease()
                        onPress("${key}_UP")
                        color.value = notPressedColor
                    }, onTap = {
                        color.value = onPressedColor
                        onTap("${key}_CLICKED")
                        color.value = notPressedColor
                    }
                )
            }
    ) {}
}

@Composable
fun ButtonShoulders(viewModel: TcpConnectionViewModel){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        //.height(80.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(32.dp),
            //horizontalAlignment = Arrangement.spacedBy(12.dp)
        ) {
            ButtonShoulderOnPressOnTap( // GamePad : L2 / LT
                key = "S",
                onPress = { viewModel.sendKeyToPc(it) },
                onTap = { viewModel.sendKeyToPc(it) }
            )
            Spacer(modifier = Modifier.size(16.dp))
            ButtonShoulderOnPressOnTap( // GamePad : L1 / LB
                key = "TAB",
                onPress = { viewModel.sendKeyToPc(it) },
                onTap = { viewModel.sendKeyToPc(it) }
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(32.dp),
                //.padding(start = 16.dp, top = 16.dp),
            //horizontalAlignment = Arrangement.spacedBy(12.dp)
        ) {
            ButtonShoulderOnPressOnTap( // GamePad : R2 / RT
                key = "C",
                onPress = { viewModel.sendKeyToPc(it) },
                onTap = { viewModel.sendKeyToPc(it) }
            )
            Spacer(modifier = Modifier.size(16.dp))
            ButtonShoulderOnPressOnTap( // GamePad : R1 / RB
                key = "E",
                onPress = { viewModel.sendKeyToPc(it) },
                onTap = { viewModel.sendKeyToPc(it) }
            )
        }
    }
}

@Composable
fun ButtonCenterOnPressOnTap(
    key: String,
    onPress: (String) -> Unit,
    onTap: (String) -> Unit,
    ){
    val haptic = LocalHapticFeedback.current
    val onPressedColor = MaterialTheme.colorScheme.onSurface
    val notPressedColor = MaterialTheme.colorScheme.surface

    val color = remember { mutableStateOf(notPressedColor)}
    Box(
        modifier = Modifier
            .size(width = 70.dp, height = 40.dp)
            .background(color = color.value)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        onPress("${key}_DOWN")
                        color.value= onPressedColor
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        tryAwaitRelease()
                        onPress("${key}_UP")
                        color.value = notPressedColor
                    }, onTap = {
                        color.value = onPressedColor
                        onTap("${key}_CLICKED")
                        color.value = notPressedColor
                    }
                )
            }
    ) {}
}

@Composable
fun ButtonCenters(viewModel: TcpConnectionViewModel){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(40.dp)
        //.height(80.dp)
    ) {
        Row (
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(32.dp),
            //horizontalAlignment = Arrangement.spacedBy(12.dp)
        ) {
            ButtonCenterOnPressOnTap(
                key = "ESC",
                onPress = {viewModel.sendKeyToPc(it)},
                onTap = {viewModel.sendKeyToPc(it)}
            )
            Spacer(modifier = Modifier.size(16.dp))
            ButtonCenterOnPressOnTap(
                key = "I",
                onPress = {viewModel.sendKeyToPc(it)},
                onTap = {viewModel.sendKeyToPc(it)}
            )

        }
    }
}