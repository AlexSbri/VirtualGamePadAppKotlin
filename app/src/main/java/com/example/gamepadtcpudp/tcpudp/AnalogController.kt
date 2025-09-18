package com.example.gamepadtcpudp.tcpudp

import android.util.Log
import androidx.compose.ui.geometry.Offset
import kotlin.math.sqrt

class AnalogController(
    private val sendCommand: (String) -> Unit
) {
    //private val threshold = 0.25f
    // -------------------
    // Sostituisci threshold assiale con radius per zona morta circolare
    private val deadZoneRadius = 0.2f  // ora soglia circolare
    // -------------------

    private var upPressed = false
    private var downPressed = false
    private var leftPressed = false
    private var rightPressed = false

    fun onAnalogMove(offset: Offset) {

        // Calcola il raggio vettoriale
        val magnitude = sqrt(offset.x * offset.x + offset.y * offset.y)

        if (magnitude < deadZoneRadius) {
            // Siamo nella zona morta circolare → rilascia tutte le direzioni
            releaseAllDirectionsIfNeeded()
            return
        }
        /*
        val up = offset.y < -threshold
        val down = offset.y > threshold
        val left = offset.x < -threshold
        val right = offset.x > threshold
         */
        // Determina contemporaneamente le direzioni possibili
        val up = offset.y < -deadZoneRadius
        val down = offset.y > deadZoneRadius
        val left = offset.x < -deadZoneRadius
        val right = offset.x > deadZoneRadius

        handleDirection("UP", up, ::upPressed) { upPressed = it }
        handleDirection("DOWN", down, ::downPressed) { downPressed = it }
        handleDirection("LEFT", left, ::leftPressed) { leftPressed = it }
        handleDirection("RIGHT", right, ::rightPressed) { rightPressed = it }
    }

    private fun handleDirection(
        direction: String,
        isPressedNow: Boolean,
        wasPressed: () -> Boolean,
        updatePressed: (Boolean) -> Unit
    ) {
        if (isPressedNow && !wasPressed()) {
            sendCommand("${direction}_DOWN")
            updatePressed(true)
        } else if (!isPressedNow && wasPressed()) {
            sendCommand("${direction}_UP")
            updatePressed(false)
        }
    }
    private fun releaseAllDirectionsIfNeeded() {
        // Invia _UP per qualsiasi direzione ancora attiva
        if (upPressed) { sendCommand("UP_UP"); upPressed = false }
        if (downPressed) { sendCommand("DOWN_UP"); downPressed = false }
        if (leftPressed) { sendCommand("LEFT_UP"); leftPressed = false }
        if (rightPressed) { sendCommand("RIGHT_UP"); rightPressed = false }
    }
}
