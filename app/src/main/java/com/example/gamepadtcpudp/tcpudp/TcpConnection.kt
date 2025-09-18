package com.example.gamepadtcpudp.tcpudp

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class TcpConnection {
    var socket: Socket ?= null

    fun connectPc(udpDiscover: UdpDiscoverData){

        Log.d("TCP CONNECTION", "udp discover address :: "+udpDiscover.tcpAddress + "\nudp discover port :: " +udpDiscover.tcpPort)
        try {
            socket = Socket(udpDiscover.tcpAddress, udpDiscover.tcpPort)
        } catch (e : Exception){
            Log.d("TcpConnection", "errore nella connessione:: $e")
        }
    }


    fun sendKey(key: String) {
        try {
            socket?.getOutputStream()?.let { stream ->
                socket?.tcpNoDelay = true
                stream.write(key.toByteArray())
                stream.flush() // Assicura che il comando venga inviato subito
                //socket?.close()
            } ?: Log.d("SENDKEY OUTPUTSTREAM","Socket o getoutput stream sono null")
        } catch (e: IOException) {
            Log.e("TcpConnection", "Errore nell'invio del comando: $e")
        }
    }

    fun closeSocket(){
        socket?.close()
        socket = null
    }

    fun isSocketAlive(): Boolean {
        return try {
            socket?.getOutputStream()?.write(0) // Provo a inviare un byte "dummy"
            socket?.getOutputStream()?.flush()
            true // Se arriva qui, il socket è attivo!
        } catch (e: IOException) {
            false // Se c'è errore, la connessione non è più attiva
        }
    }

}

