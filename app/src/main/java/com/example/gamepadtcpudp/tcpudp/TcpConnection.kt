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

    fun connectPc(udpDiscover: UdpDiscoverResult){
        when(udpDiscover){
            is UdpDiscoverResult.Found -> {
                val address = udpDiscover.tcpAddress
                val port = udpDiscover.port
                Log.d("Connect to Pc","Trovato : $address:$port")

                try {
                    socket = Socket(address, port)
                } catch (e : Exception){
                    Log.d("TcpConnection", "errore nella connessione:: $e")
                }
            }

            is UdpDiscoverResult.NotFound -> Log.d("Connect to Pc","Pc non trovato")
            UdpDiscoverResult.AwaitingDiscovery -> Log.d("Connect to Pc","Fare prima la ricerca del pc")
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

